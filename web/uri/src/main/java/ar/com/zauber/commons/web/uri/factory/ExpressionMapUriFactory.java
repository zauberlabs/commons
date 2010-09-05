/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ar.com.zauber.commons.web.uri.factory;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * Factory para generar uris.
 * 
 * <p>
 * Cada uri tiene una clave, y una expresión que al ser evaluada construye la
 * URI. La expresión se evalua con un {@link ExpressionParser}. Un ejemplo de
 * este es el {@link SpelExpressionParser}.
 * 
 * <p>
 * Las expresiones son similares a las del UriTemplate de Spring. Algunos
 * ejemplos con <em>SpEl</em>:
 * 
 * <ul>
 * <li> <code>/users/{#root[0]}/inbox</code></li>
 * <li> <code>/users/{#root[0]}/questions/{#root[1].id}</code></li>
 * <li> <code>/users/{#root[0].username}/</code></li>
 * </ul>
 * 
 * <p>
 * El contenido entre los <code>{}</code> es evaluado por el lenguaje. La
 * variable <code>#root</code> refiere al array de argumentos que se pasan al
 * método {@link ExpressionMapUriFactory#buildUri(String, Object...)}.
 * Además puede invocarse a la función #encode que llama a URLEncoder#encode(...)
 * para encoding utf-8.
 * 
 * @author Mariano Cortesi
 * @since Jan 29, 2010
 */
public class ExpressionMapUriFactory implements UriFactory {

    private final Map<String, Expression> uriMap = new HashMap<String, Expression>();
    private final Method encodeMethod;

    /** Construye un UriFactor */
    public ExpressionMapUriFactory(final ExpressionParser parser, 
            final Map<String, String> uris) {
        Validate.notNull(uris);
        Validate.notNull(parser);
        ParserContext parserContext = new TemplateParserContext("{", "}");
        
        for (Map.Entry<String, String> uriConf : uris.entrySet()) {
            uriMap.put(uriConf.getKey(), parser.parseExpression(uriConf
                    .getValue(), parserContext));
        }
        
        try {
            encodeMethod = getClass().getDeclaredMethod(
                    "encodeUtf8", String.class);
        } catch(final NoSuchMethodException e) {
            throw new UnhandledException(e);
        }
        
    }

    /** @see UriFactory#buildUri(String, Object) */
    public final String buildUri(final String uriKey, final Object... expArgs) {
        Validate.notNull(uriKey);
        Validate.isTrue(uriMap.containsKey(uriKey), "No key named `" + uriKey
                + "'");
        Validate.noNullElements(expArgs);
        
        final StandardEvaluationContext ctx = new StandardEvaluationContext(expArgs);
        ctx.registerFunction("encode", encodeMethod);
        return uriMap.get(uriKey).getValue(ctx, String.class);
    }
    
    /**
     * Esta función se usa para ser registrada en el contexto de evaluación por
     * reflexión.
     * @return la url codificada usando utf-8.
     * @throws UnsupportedEncodingException 
     */
    public static String encodeUtf8(final String url)
            throws UnsupportedEncodingException {
        final String ret = URLEncoder.encode(url, "utf-8");
        
        return ret.replace("+", "%20");
    }
}

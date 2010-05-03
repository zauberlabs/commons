/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.factory;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

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
 * método {@link RelativeUriFactory#buildUri(String, Object...)}.
 * Además puede invocarse a la función #encode que llama a URLEncoder#encode(...)
 * para encoding utf-8.
 * 
 * @author Mariano Cortesi
 * @since Jan 29, 2010
 */
public class RelativeUriFactory implements UriFactory {

    private final Map<String, Expression> uriMap = new HashMap<String, Expression>();
    
    private final StandardEvaluationContext ctx;

    /** Construye un UriFactor */
    public RelativeUriFactory(final ExpressionParser parser, 
            final Map<String, String> uris) {
        Validate.notNull(uris);
        Validate.notNull(parser);
        ParserContext parserContext = new TemplateParserContext("{", "}");
        
        for (Map.Entry<String, String> uriConf : uris.entrySet()) {
            uriMap.put(uriConf.getKey(), parser.parseExpression(uriConf
                    .getValue(), parserContext));
        }
        
        Method encodeMethod;
        try {
            encodeMethod = getClass().getDeclaredMethod(
                    "encodeUtf8", String.class);
        } catch(final NoSuchMethodException e) {
            throw new RuntimeException();
        }
        
        this.ctx = new StandardEvaluationContext();
        this.ctx.registerFunction("encode", encodeMethod);
    }

    /** @see UriFactory#buildUri(String, Object) */
    public final String buildUri(final String uriKey, final Object... expArgs) {
        Validate.notNull(uriKey);
        Validate.noNullElements(expArgs);
        Validate.isTrue(this.uriMap.containsKey(uriKey));
        
        ctx.setRootObject(expArgs);
        
        return this.uriMap.get(uriKey).getValue(ctx, String.class);
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

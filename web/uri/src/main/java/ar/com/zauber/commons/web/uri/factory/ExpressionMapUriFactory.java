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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.util.UriTemplate;

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
 * @author Juan F. Codagnone (uri template support)
 * @since Jan 29, 2010
 */
public class ExpressionMapUriFactory implements UriFactory {

    private final Map<String, UriExpression> uriMap;
    private static final Method ENCODE_METHOD;
    static {
        try {
            ENCODE_METHOD = ExpressionMapUriFactory.class.getDeclaredMethod(
                    "encodeUtf8", String.class);
        } catch(final NoSuchMethodException e) {
            throw new UnhandledException(e);
        }
    }
    
    /** tipo  de expressión */
    public static interface ExpressionTemplate {
        /** @return an human representation for the expression */
        String getExpressionString();
        
        /** @return a resolved uri */
        String resolveUri(Object ...params);
    }
    
    /** crea expressiones en base a strings */
    public static interface ExpressionTemplateFactory {
        /** crea expresiones */
        ExpressionTemplate create(String expression);
    }
    
    /** @see Expression */
    public static class SpringExpressionTemplate implements ExpressionTemplate {
        private final Expression expression;

        /** Creates the ExpressionMapUriFactory.SpringExpressionTemplate. */
        public SpringExpressionTemplate(final Expression expression) {
            Validate.notNull(expression);
            
            this.expression = expression;
        }
        public final String getExpressionString() {
            return expression.getExpressionString();
        }

        /** @see ExpressionMapUriFactory.ExpressionTemplate#resolveUri(Object[]) */
        public final String resolveUri(final Object... expArgs) {
          final StandardEvaluationContext ctx = 
              new StandardEvaluationContext(expArgs);
          ctx.registerFunction("encode", ENCODE_METHOD);
          return expression.getValue(ctx, String.class);
        }
    }
    
    /** create {@link ExpressionTemplateFactory} */
    public static  class SpringExpressionTemplateFactory 
              implements ExpressionTemplateFactory {
        private final ExpressionParser parser;
        private final TemplateParserContext ctx = 
            new TemplateParserContext("{", "}");
        /** */
        public SpringExpressionTemplateFactory(final ExpressionParser parser) {
            Validate.notNull(parser);
            
            this.parser = parser;
            
        }
        /** @see ExpressionTemplateFactory#create(String) */
        public final ExpressionTemplate create(final String expr) {
            return new SpringExpressionTemplate(parser.parseExpression(expr, ctx));
        }
    }
    
    /** Utiliza {@link UriTemplate} para hidratar las uris */
    public static class UriTemplateExpression implements ExpressionTemplate {
        private final UriTemplate uriTemplate;

        /** Creates the ExpressionMapUriFactory.UriTemplateExpression. */
        public UriTemplateExpression(final UriTemplate uriTemplate) {
            Validate.notNull(uriTemplate);
            
            this.uriTemplate = uriTemplate;
        }
        
        /** @see ExpressionMapUriFactory.ExpressionTemplate#getExpressionString() */
        public final String getExpressionString() {
            return uriTemplate.toString();
        }

        /** @see ExpressionTemplate#resolveUri(Object[]) */
        public final String resolveUri(final Object... params) {
            return uriTemplate.expand(params).toString();
        }
    }

    /** crea {@link UriTemplateExpression} */
    public static class UriTemplateExpressionFactory 
             implements ExpressionTemplateFactory {
        /** @see ExpressionTemplateFactory#create(String) */
        public final ExpressionTemplate create(final String expression) {
            return new UriTemplateExpression(new UriTemplate(expression));
        }
    }
    /** Describe una expresión */
    public static class UriExpression {
        /** expresion */
        public final ExpressionTemplate expression;
        /** descripcion del recurso al que apunta la expresión (opcional) */
        public final String description;
        
        /** Creates the ExpressionMapUriFactory.UriExpression. */
        public UriExpression(final ExpressionTemplate expression) {
            Validate.notNull(expression);
            
            this.expression = expression;
            this.description = null;
        }
        
        /** Creates the ExpressionMapUriFactory.UriExpression. */
        public UriExpression(final ExpressionTemplate expression, 
                                   final String description) {
            Validate.notNull(expression);
            
            this.expression = expression;
            this.description = description;
        }
        
        /** @see Object#toString() */
        @Override
        public final String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("expr", expression.getExpressionString())
                    .append("description", description).toString();
        }
    }

    /**************** CONSTRUCTORES ***********************************/
    /** tipo de expresion a construir  */
    public enum Type {
        /** spring el */   
        SPEL,
        /** uritemplate */ 
        URITEMPLATE
    }
    
    /** Construye un UriFactor utilizando {@link UriTemplate}  */
    public ExpressionMapUriFactory(final Map<String, String> uris,
            final Type type) {
        this(createUriExpression(uris, type));
    }
    
    /** Construye un UriFactor utilizando Spring expression Language */
    public ExpressionMapUriFactory(final ExpressionParser parser, 
            final Map<String, String> uris) {
        this(createUriExpression(new SpringExpressionTemplateFactory(parser), uris));
    }
    
    /** Construye un UriFactor */
    public ExpressionMapUriFactory(final Map<String, UriExpression> uris) {
        Validate.notNull(uris);
        this.uriMap = uris;
    }
    
    /** construye en base a string {@link UriExpression} */
    private static Map<String, UriExpression> createUriExpression(
            final Map<String, String> uris, final Type type) {
        return createUriExpression(type == Type.SPEL 
                ? new SpringExpressionTemplateFactory(new SpelExpressionParser())
                : new UriTemplateExpressionFactory(), uris);
    }
    
    /** create expression map */
    private static Map<String, UriExpression> createUriExpression(
            final ExpressionTemplateFactory factory, 
            final Map<String, String> uris) {
        final Map<String, UriExpression> map = new HashMap<String, UriExpression>();
        for (final  Map.Entry<String, String> uriConf : uris.entrySet()) {
            map.put(uriConf.getKey(),
                new UriExpression(factory.create(uriConf.getValue())));
        }
        return map;
    }
    
    /****************** METODO DE SERVICIO ***********************************/
    
    /** @see UriFactory#buildUri(String, Object) */
    public final String buildUri(final String uriKey, final Object... expArgs) {
        Validate.notNull(uriKey);
        Validate.isTrue(uriMap.containsKey(uriKey), "No key named `" + uriKey
                + "'");
        Validate.noNullElements(expArgs);
        

        return uriMap.get(uriKey).expression.resolveUri(expArgs);
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
    
    public final Map<String, UriExpression> getUriMap() {
        return Collections.unmodifiableMap(uriMap);
    }
}

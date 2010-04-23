/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.factory;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * Factory para generar uris.
 * 
 * <p>
 * Cada uri tiene una clave, y una expresi�n que al ser evaluada construye la
 * URI. La expresi�n se evalua con un {@link ExpressionParser}. Un ejemplo de
 * este es el {@link SpelExpressionParser}.
 * 
 * <p>
 * Las expresiones son similares a las del UriTemplate de Spring. Algunos
 * ejemplos con <em>SpEl</em>:
 * 
 * <ul>
 * <li> <code>/users/{#root[0]}/inbox</code>
 * <li> <code>/users/{#root[0]}/questions/{#root[1].id}</code>
 * <li> <code>/users/{#root[0].username}/</code>
 * </ul>
 * 
 * <p>
 * El contenido entre los <code>{}</code> es evaluado por el lenguaje. La
 * variable <code>#root</code> refiere al array de argumentos que se pasan al
 * m�todo {@link RelativeUriFactory#buildUri(String, Object...)}.
 * 
 * @author Mariano Cortesi
 * @since Jan 29, 2010
 */
public class RelativeUriFactory implements UriFactory {

    private final Map<String, Expression> uriMap = new HashMap<String, Expression>();

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
    }

    /** @see UriFactory#buildUri(String, Object) */
    public final String buildUri(final String uriKey, final Object... expArgs) {
        Validate.notNull(uriKey);
        Validate.noNullElements(expArgs);
        Validate.isTrue(this.uriMap.containsKey(uriKey));
        return this.uriMap.get(uriKey).getValue(expArgs, String.class);
    }
}

/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.web.uri;

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
 * Cada uri tiene una clave, y una expresión que al ser evaluada construye la
 * URI. La expresión se evalua con un {@link ExpressionParser}. Un ejemplo de
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
 * método {@link UriFactory#buildUri(String, Object...)}.
 * 
 * <p>
 * A su vez, el {@link UriFactory} recibe un <em>host</em> y un
 * <em>basePath</em> que se preapendean al string generado por la expresión.
 * 
 * 
 * @author Mariano Cortesi
 * @since Jan 29, 2010
 */
public class UriFactory {

    private final Map<String, Expression> uriMap = new HashMap<String, Expression>();
    private final String host;
    private final String basePath;

    /** Construye un UriFactor */
    public UriFactory(final String host, final String basePath,
            final ExpressionParser parser, final Map<String, String> uris) {
        Validate.notNull(host);
        Validate.notNull(uris);
        Validate.notNull(parser);
        Validate.notNull(basePath);
        this.host = host;
        this.basePath = basePath;
        ParserContext parserContext = new TemplateParserContext("{", "}");
        for (Map.Entry<String, String> uriConf : uris.entrySet()) {
            uriMap.put(uriConf.getKey(), parser.parseExpression(uriConf
                    .getValue(), parserContext));
        }
    }

    /**
     * Construye la uri referida por uriKey con los parametros expArgs.
     * 
     * @param uriKey
     *            Clave del Uri
     * @param expArgs
     *            Parametros de la expresión referida por el uriKey.
     * @return uri generado.
     */
    public final String buildUri(final String uriKey, final Object... expArgs) {
        return buildUri(true, uriKey, expArgs);
    }

    /**
     * Construye la uri referida por uriKey con los parametros expArgs, sin
     * appendear el host
     * 
     * @param appendHost
     *            Si es false no se incluirá el <em>host</em> al inicio de la
     *            uri
     * @param uriKey
     *            Clave del Uri
     * @param expArgs
     *            Parametros de la expresión referida por el uriKey.
     * @return uri generado.
     */
    public final String buildUri(final boolean appendHost, final String uriKey,
            final Object... expArgs) {
        Validate.notNull(uriKey);
        Validate.noNullElements(expArgs);
        Validate.isTrue(this.uriMap.containsKey(uriKey));
        StringBuilder out = new StringBuilder();

        if (appendHost) {
            out.append(this.host);
        }

        out.append(this.basePath).append(
                this.uriMap.get(uriKey).getValue(expArgs, String.class));

        return out.toString();
    }
}

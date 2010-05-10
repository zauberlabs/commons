/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.web.uri;

import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.Validate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import ar.com.zauber.commons.web.uri.factory.ExpressionMapUriFactory;
import ar.com.zauber.commons.web.uri.factory.UriFactory;

/**
 * Jsp 2.0 Functions para ser llamadas por desde JSP EL, con la función de
 * construir uris.
 * 
 * <p>
 * Para ello, buscan un {@link ExpressionMapUriFactory} en el
 * {@link PageContext} con la clave {@link #URI_FACTORY_KEY}
 * 
 * 
 * @author Mariano Cortesi
 * @since Jan 29, 2010
 */
public final class UriJspFunctions {

    /** utility class */
    private UriJspFunctions() {
        // void
    }

    /** Construye un uri */
    public static String buildVarArgs(final PageContext ctx,
            final String uriKey, final Object... params) {
        Validate.notNull(uriKey);
        Validate.notNull(ctx);
        
        WebApplicationContext appCtx = 
            RequestContextUtils.getWebApplicationContext(ctx.getRequest());
        
        UriFactory uriFactory = appCtx.getBean(
                SpringBeans.ASSET_URIFACTORY_KEY, UriFactory.class);
        
        return uriFactory.buildUri(uriKey, params);
    }

    /** Construye un uri a través de un <em>uriKey</em> y parametros */
    public static String build(final PageContext ctx,
            final String uriKey) {
        return buildVarArgs(ctx, uriKey);
    }

    /** Construye un uri a través de un <em>uriKey</em> y parametros */
    public static String build(final PageContext ctx,
            final String uriKey, final Object p1) {
        return buildVarArgs(ctx, uriKey, p1);
    }

    /** Construye un uri a través de un <em>uriKey</em> y parametros */
    public static String build(final PageContext ctx,
            final String uriKey, final Object p1, final Object p2) {
        return buildVarArgs(ctx, uriKey, p1, p2);
    }

    /** Construye un uri a través de un <em>uriKey</em> y parametros */
    public static String build(final PageContext ctx,
            final String uriKey, final Object p1, final Object p2,
            final Object p3) {
        return buildVarArgs(ctx, uriKey, p1, p2, p3);
    }

    /** Construye un uri a través de un <em>uriKey</em> y parametros */
    public static String build(final PageContext ctx,
            final String uriKey, final Object p1, final Object p2,
            final Object p3, final Object p4) {
        return buildVarArgs(ctx, uriKey, p1, p2, p3, p4);
    }

    /** Construye un uri a través de un <em>uriKey</em> y parametros */
    public static String build(final PageContext ctx,
            final String uriKey, final Object p1, final Object p2,
            final Object p3, final Object p4, final Object p5) {
        return buildVarArgs(ctx, uriKey, p1, p2, p3, p4, p5);
    }
}

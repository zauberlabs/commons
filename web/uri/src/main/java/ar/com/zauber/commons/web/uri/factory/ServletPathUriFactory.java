/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.factory;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

/**
 * {@link UriFactory} implementation that adds the servlet path
 * as a prefix
 * 
 * @author Mariano Cortesi
 * @since May 7, 2010
 */
public class ServletPathUriFactory implements UriFactory, ServletContextAware {

    private String contextPath;
    private final UriFactory uriFactory;
    
    /**
     * Creates the ServletPathUriFactory.
     *
     * @param uriFactory decorated {@link UriFactory}
     */
    public ServletPathUriFactory(final UriFactory uriFactory) {
        this.uriFactory = uriFactory;
    }

    /** @see UriFactory#buildUri(String, Object[]) */
    public final String buildUri(final String uriKey, final Object... expArgs) {
        return new StringBuilder(contextPath)
            .append(uriFactory.buildUri(uriKey, expArgs))
            .toString();
    }

    /** @see ServletContextAware#setServletContext(ServletContext) */
    public final void setServletContext(final ServletContext servletContext) {
        this.contextPath = servletContext.getContextPath();
    }

}

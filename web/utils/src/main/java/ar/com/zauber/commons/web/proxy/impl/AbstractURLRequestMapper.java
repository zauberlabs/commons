/*
 * Copyright (c) 2008 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.proxy.impl;

import javax.servlet.http.HttpServletRequest;

import ar.com.zauber.commons.web.proxy.URLRequestMapper;

/**
 * Base {@link URLRequestMapper} that contains all the configuration
 * and behaviour for striping the context path and the servlet path.
 * 
 * @author Juan F. Codagnone
 * @since Aug 29, 2008
 */
public abstract class AbstractURLRequestMapper implements URLRequestMapper {
    private boolean stripContextPath = true;
    private boolean stripServletPath = true;
    
    public final boolean isStripContextPath() {
        return stripContextPath;
    }

    /**
     * <code>true</code> to strip the context path from the context path (for
     * example application-web-version/)
     */
    public final void setStripContextPath(final boolean stripContextPath) {
        this.stripContextPath = stripContextPath;
    }

    public final boolean isStripServletPath() {
        return stripServletPath;
    }

    /**
     * <code>true</code> to strip the servlet path from the proxied url (for
     * example /bin/)
     */
    public final void setStripServletPath(final boolean stripServletPath) {
        this.stripServletPath = stripServletPath;
    }

    /** optionaly strips the context path, and the servlet path */
    protected final String getRequestURI(final HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (stripContextPath) {
            uri = uri.substring(request.getContextPath().length());

        }
        if (stripServletPath) {
            uri = uri.substring(request.getServletPath().length());
        }

        return uri;
    }
}

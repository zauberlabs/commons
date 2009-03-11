/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Filter that does nothing. 
 *
 * @author Juan F. Codagnone
 * @since Mar 10, 2009
 */
public class NullFilter implements Filter {

    /** @see Filter#destroy() */
    public final void destroy() {
        // nothing to do
    }

    /** @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain) */
    public final void doFilter(final ServletRequest request, 
            final ServletResponse response,
            final FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
    }

    /** @see Filter#init(FilterConfig) */
    public void init(final FilterConfig filterConfig) throws ServletException {
        // nothing to do
    }
}

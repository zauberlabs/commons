/*
 * Copyright (c) 2005-2008 Zauber S.A. <http://www.zauber.com.ar/>
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

package ar.com.zauber.commons.web.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 *  {@link Filter} that adds http headers to the response.
 *  Usage:
 *    
 *  <pre>
 *    <filter>
 *    <filter-name>ResponseHeaderFilter</filter-name>
 *    <filter-class>...zauber.commons.web.filter.ResponseHeaderFilter</filter-class>
 *    <init-param>
 *       <param-name>Cache-Control</param-name>
 *       <param-value>max-age=604800</param-value>
 *    </init-param>
 *  </filter>
 *
 *  </pre>
 */
public class ResponseHeaderFilter implements Filter {

    private FilterConfig fc;

    /** @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain) */
    public final void doFilter(final ServletRequest req, final ServletResponse res,
            final FilterChain chain) throws IOException, ServletException {
        final HttpServletResponse response = (HttpServletResponse)res;
        // set the provided HTTP response parameters
        for(final Enumeration e = fc.getInitParameterNames(); e.hasMoreElements();) {
            final String headerName = (String)e.nextElement();
            response.addHeader(headerName, fc.getInitParameter(headerName));
        }
        // pass the request/response on
        chain.doFilter(req, response);
    }

    /** @see Filter#init(FilterConfig) */
    public final void init(final FilterConfig filterConfig) {
        fc = filterConfig;
    }

    /** @see Filter#destroy() */
    public final void destroy() {
        fc = null;
    }
}
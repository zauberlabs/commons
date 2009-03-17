/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
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

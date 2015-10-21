/**
 * Copyright (c) 2005-2015 Zauber S.A. <http://flowics.com/>
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
		String uri = getCompleteUri(request);
        if (stripContextPath) {
            uri = uri.substring(request.getContextPath().length());

        }
        if (stripServletPath) {
            uri = uri.substring(request.getServletPath().length());
        }

        return uri;
    }

	private String getCompleteUri(final HttpServletRequest request) {
		return request.getQueryString() != null 
				? request.getRequestURI() + '?' + request.getQueryString() 
				: request.getRequestURI();
	}
}

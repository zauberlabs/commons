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

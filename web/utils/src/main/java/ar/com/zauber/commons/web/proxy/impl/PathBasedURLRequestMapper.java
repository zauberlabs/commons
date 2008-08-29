/*
 * Copyright (c) 2005-2008 Zauber S.A. <http://www.zauber.com.ar/>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package ar.com.zauber.commons.web.proxy.impl;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.proxy.URLRequestMapper;
import ar.com.zauber.commons.web.proxy.URLResult;

/**
 * <p>
 * {@link URLRequestMapper} that given a base url (that is acquired using
 *  another {@link URLRequestMapper} [for example the 
 *  {@link InmutableURLRequestMapper}]) appends the paths of the request.
 *  It is posible to strip the context path and the servlet path from the request
 *  to get the new URL.
 *  </p>  
 *  <p>
 *   So, for example, if the request contains the URI
 *   <tt>/nexusaaa-0.0/bin/doit</tt>, and <tt>/nexusaaa-0.0</tt> is the 
 *   context path, and <tt>/bin</tt> is the servlet path, and the base
 *   {@link URLRequestMapper} used is an {@link InmutableURLRequestMapper}
 *   that has the URL <tt>http://localhost:9095/nexus</tt> the mapper would 
 *   resolve the url  <tt>http://localhost:9095/nexus/doit</tt> (by default
 *   the servlet path and the context path are striped).
 * </p>
 * 
 * @author Juan F. Codagnone
 * @since Aug 28, 2008
 */
public class PathBasedURLRequestMapper extends AbstractURLRequestMapper {
    private URLRequestMapper base;
    

    /** constructor */
    public PathBasedURLRequestMapper(final URLRequestMapper base) {
        Validate.notNull(base);

        this.base = base;
    }

    /** @see URLRequestMapper#getProxiedURLFromRequest(HttpServletRequest) */
    public final URLResult getProxiedURLFromRequest(
            final HttpServletRequest request) {
                URLResult r = base.getProxiedURLFromRequest(request);
        if(r.hasResult()) {
            try {
                r = new InmutableURLResult(new URL(r.getURL().toExternalForm()
                        + getRequestURI(request)));
            } catch (final MalformedURLException e) {
                throw new UnhandledException(e);
            }
        }
        return r;
    }
}

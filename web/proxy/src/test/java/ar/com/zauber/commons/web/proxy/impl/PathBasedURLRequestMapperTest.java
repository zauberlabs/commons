/**
 * Copyright (c) 2005-2011 Zauber S.A. <http://www.zaubersoftware.com/>
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


import java.net.URL;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockServletContext;

/**
 * Test for {@link PathBasedURLRequestMapper}.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Aug 28, 2008
 */
public class PathBasedURLRequestMapperTest extends TestCase {
    private final String base = "http://localhost:9095/nexus";

    /** @throws Exception on error */
    public final void testServletContext() throws Exception {
        final String ctxPath = "/nexusaaa-0.0";
        final String servletContext = "/bin";

        final PathBasedURLRequestMapper mapper =  new PathBasedURLRequestMapper(
                new InmutableURLRequestMapper(new InmutableURLResult(
                        new URL(base))));
        mapper.setStripContextPath(true);
        mapper.setStripServletPath(true);
        
        final MockServletContext ctx = new MockServletContext();

        final MockHttpServletRequest request = new MockHttpServletRequest(ctx,
                "GET", ctxPath + servletContext + "/");
        request.setContextPath(ctxPath);
        request.setServletPath(servletContext);
        assertEquals(new URL(base + "/"), 
                mapper.getProxiedURLFromRequest(request).getURL());
    }
    
    /** @throws Exception on error */
    public final void testNoServletContext() throws Exception {
        final String ctxPath = "/nexusaaa-0.0";
        final String servletContext = "/bin";

        final PathBasedURLRequestMapper mapper =  new PathBasedURLRequestMapper(
                new InmutableURLRequestMapper(new InmutableURLResult(
                        new URL(base))));
        mapper.setStripContextPath(false);
        mapper.setStripServletPath(false);
        
        final MockServletContext ctx = new MockServletContext();

        final MockHttpServletRequest request = new MockHttpServletRequest(ctx,
                "GET", ctxPath + servletContext + "/");
        request.setContextPath(ctxPath);
        request.setServletPath(servletContext);
        assertEquals(new URL(base + ctxPath + servletContext + "/"), 
                mapper.getProxiedURLFromRequest(request).getURL());
    }
    
    /** @throws Exception on error */
    public final void testResultDelegate() throws Exception {
        final PathBasedURLRequestMapper mapper =  new PathBasedURLRequestMapper(
                new InmutableURLRequestMapper(new InmutableURLResult()));
        final MockServletContext ctx = new MockServletContext();

        final String ctxPath = "/nexusaaa-0.0";
        final String servletContext = "/bin";

        final MockHttpServletRequest request = new MockHttpServletRequest(ctx,
                "GET", ctxPath + servletContext + "/");
        request.setContextPath(ctxPath);
        request.setServletPath(servletContext);
        
        assertFalse(mapper.getProxiedURLFromRequest(request).hasResult());
    }
}

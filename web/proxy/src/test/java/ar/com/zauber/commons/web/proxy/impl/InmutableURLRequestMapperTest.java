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

import java.net.URL;

import org.springframework.mock.web.MockHttpServletRequest;

import junit.framework.TestCase;
import ar.com.zauber.commons.web.proxy.URLRequestMapper;

/**
 * Tests for {@link InmutableURLRequestMapper}
 * 
 * 
 * @author Juan F. Codagnone
 * @since Aug 28, 2008
 */
public class InmutableURLRequestMapperTest extends TestCase {

    /** @throws Exception on error */
    public final void testNull() throws Exception {
        try {
            new InmutableURLRequestMapper(null);
            fail();
        } catch(IllegalArgumentException e) {
            // ok
        }
    }
    
    /** @throws Exception on error */
    public final void testAny() throws Exception {
        final URL url = new URL("http://127.0.0.1/foo/bar");
        final URLRequestMapper mapper =  new InmutableURLRequestMapper(
                new InmutableURLResult(url));
        final String ctxPath = "/nexusaaa-0.0";
        final String servletContext = "/bin";
        
        final MockHttpServletRequest request = new MockHttpServletRequest(
                "GET", ctxPath + servletContext + "/");
        request.setContextPath(ctxPath);
        request.setServletPath(servletContext);
        
        assertEquals(url, mapper.getProxiedURLFromRequest(request).getURL());
    }
}

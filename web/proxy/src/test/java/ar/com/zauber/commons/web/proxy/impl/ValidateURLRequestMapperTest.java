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

import javax.servlet.http.HttpServletRequest;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;

import ar.com.zauber.commons.web.proxy.URLRequestMapper;
import ar.com.zauber.commons.web.proxy.URLResult;

/**
 * Test for {@link ValidateURLRequestMapper}.
 * 
 * @author Juan F. Codagnone
 * @since Aug 28, 2008
 */
public class ValidateURLRequestMapperTest extends TestCase {

    
    /** @throws Exception on error */
    public final void testConstructor() throws Exception {
        try {
            new ValidateURLRequestMapper(null);
            fail();
        } catch(IllegalArgumentException e) {
            // ok
        }
    }
    
    
    /** @throws Exception on error */
    public final void testNullArgument() throws Exception {
        final URLRequestMapper v = new ValidateURLRequestMapper(
                new InmutableURLRequestMapper(new InmutableURLResult(
                        new URL("http://localhost"))));
        try {
            v.getProxiedURLFromRequest(null);
            fail();
        } catch(final IllegalArgumentException e) {
            assertEquals("request can't be null", e.getMessage());
        }
    }
    
    

    /** @throws Exception on error */
    public final void testNullReturn() throws Exception {
        final URLRequestMapper v = new ValidateURLRequestMapper(
                new URLRequestMapper() {
                    public URLResult getProxiedURLFromRequest(
                            final HttpServletRequest request) {
                        return null;
                    }
                });
        try {
            v.getProxiedURLFromRequest(new MockHttpServletRequest());
            fail();
        } catch(final IllegalArgumentException e) {
            assertEquals("a retured URL from URLRequestMapper can't be null", 
                    e.getMessage());
        }
    }
}

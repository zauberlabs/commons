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
package ar.com.zauber.commons.web.proxy.impl;

import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * Tests for {@link MutableURLRequestMapper}.
 * 
 * @author Juan F. Codagnone
 * @since Aug 29, 2008
 */
public class MutableURLRequestMapperTest extends TestCase {

    /** test */
    public final void testNull() {
        final MutableURLRequestMapper c = new MutableURLRequestMapper();
        assertFalse(c.getProxiedURLFromRequest(
                new MockHttpServletRequest()).hasResult());
        c.setTarget(null);
        assertFalse(c.getProxiedURLFromRequest(
                new MockHttpServletRequest()).hasResult());
    }
    
    /**  @throws MalformedURLException on error */
    public final void testChange() throws MalformedURLException {
        final MutableURLRequestMapper c = new MutableURLRequestMapper(
                new InmutableURLRequestMapper(
                        new InmutableURLResult(new URL("http://localhost"))));
        assertEquals(new URL("http://localhost"), c.getProxiedURLFromRequest(
                new MockHttpServletRequest()).getURL());
        c.setTarget(new InmutableURLRequestMapper(
                new InmutableURLResult(new URL("http://127.0.0.1"))));
        assertEquals(new URL("http://127.0.0.1"), c.getProxiedURLFromRequest(
                new MockHttpServletRequest()).getURL());
    }
}

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
import ar.com.zauber.commons.web.proxy.URLResult;


/**
 * Test for {@link InmutableURLResult}. 
 * 
 * @author Juan F. Codagnone
 * @since Aug 29, 2008
 */
public class InmutableURLResultTest extends TestCase {

    /** test */
    public final void testNoValue() {
        final URLResult r = new InmutableURLResult();
        assertEquals(false, r.hasResult());
        try {
            r.getURL();
            fail();
        } catch(final IllegalStateException e) {
            // ok
        }
    }
    
    /** @throws MalformedURLException on error */
    public final void testValue() throws MalformedURLException {
        final URLResult r = new InmutableURLResult(new URL("http://localhost"));
        assertEquals(true, r.hasResult());
        assertEquals(new URL("http://localhost"), r.getURL());
    }
    
    /** @throws MalformedURLException on error */
    public final void testEquals() throws MalformedURLException {
        assertEquals(new InmutableURLResult(), new InmutableURLResult());
        final URLResult r = new InmutableURLResult();
        assertEquals(r, r);
        
        assertEquals(new InmutableURLResult(new URL("http://localhost")), 
                     new InmutableURLResult(new URL("http://localhost")));
        
        InmutableURLResult a = new InmutableURLResult(new URL("http://localhost"));
        assertEquals(a, a);
        
        assertFalse(!new InmutableURLResult(new URL("http://localhost")).equals(
                new InmutableURLResult(new URL("http://localhost"))));
    }
    
    /** @throws MalformedURLException on error */
    public final void testHash() throws MalformedURLException {
        new InmutableURLResult().hashCode();
        new InmutableURLResult(new URL("http://localhost")).hashCode();
    }
}

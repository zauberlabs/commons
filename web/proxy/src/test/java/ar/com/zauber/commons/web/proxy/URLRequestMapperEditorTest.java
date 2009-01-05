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
package ar.com.zauber.commons.web.proxy;

import java.beans.PropertyEditor;
import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * Test for {@link URLRequestMapper}.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Aug 29, 2008
 */
public class URLRequestMapperEditorTest extends TestCase {

    /** foo */
    public final void testNull() {
        final PropertyEditor pe = new URLRequestMapperEditor();
        pe.setAsText("          ");
        assertNull(pe.getValue());
    }
    
    /** foo */
    public final void testInvalidLine() {
        final PropertyEditor pe = new URLRequestMapperEditor();
        try {
            pe.setAsText("a = b          \n"
                       + "c\n");
            fail();
        } catch(IllegalArgumentException e) {
            // ok
        }
    }
    
    
    /** foo */
    public final void testEmptyLines() {
        final PropertyEditor pe = new URLRequestMapperEditor();
        pe.setAsText(
                "    \n"
              + "a = b          \n");
    }
    

    /** @throws MalformedURLException on error */
    public final void testFactory() throws MalformedURLException {
        final PropertyEditor pe = new URLRequestMapperEditor();
        pe.setAsText(
              "^/public/(.*)$=http://localhost:9095/nexus/content/"
                + "repositories/public/$1\n"
            + "^/nexus/(.*)$=http://localhost:9095/nexus/$1\n");
        
        final URLRequestMapper c = (URLRequestMapper) pe.getValue();
        assertEquals(new URL("http://localhost:9095/nexus/foo/bar"), 
                c.getProxiedURLFromRequest(new MockHttpServletRequest(
                "GET", "/nexus/foo/bar")).getURL());
    }
}

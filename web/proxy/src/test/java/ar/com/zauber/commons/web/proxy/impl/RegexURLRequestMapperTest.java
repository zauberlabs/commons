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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

import org.springframework.mock.web.MockHttpServletRequest;

import ar.com.zauber.commons.web.proxy.URLRequestMapper;
import junit.framework.TestCase;

/**
 * test for {@link RegexURLRequestMapper}.
 * 
 * @author Juan F. Codagnone
 * @since Aug 29, 2008
 */
public class RegexURLRequestMapperTest extends TestCase {

    /**  @throws MalformedURLException */
    public final void testDir() throws MalformedURLException {
        final URLRequestMapper r =  new RegexURLRequestMapper(
                new InmutableURLRequestMapper(
                        new InmutableURLResult(new URL(
                         "http://localhost:9095/nexus/content/repositories/"))),
                Pattern.compile("^/([^/]+)/([^/]+)/([^/]+)/(.*)$"),
                        "$1-$2-$3/$4");

        
        final MockHttpServletRequest request = new MockHttpServletRequest(
                "GET", "/zauber/code/releases/foo/hola");
        assertEquals(
                new URL("http://localhost:9095/nexus/content/repositories/" 
                        + "zauber-code-releases/"
                        + "foo/hola"),
                r.getProxiedURLFromRequest(request).getURL());
    }
    
    /**  @throws MalformedURLException */
    public final void testDirString() throws MalformedURLException {
        final URLRequestMapper r =  new RegexURLRequestMapper(
               Pattern.compile("^/([^/]+)/([^/]+)/([^/]+)/(.*)$"),
              "http://localhost:9095/nexus/content/repositories/$1-$2-$3/$4");

        
        final MockHttpServletRequest request = new MockHttpServletRequest(
                "GET", "/zauber/code/releases/foo/hola");
        assertEquals(
                new URL("http://localhost:9095/nexus/content/repositories/" 
                        + "zauber-code-releases/"
                        + "foo/hola"),
                r.getProxiedURLFromRequest(request).getURL());
    }
    
    /**  @throws MalformedURLException */
    public final void testEmpty() throws MalformedURLException {
        final URLRequestMapper r =  new RegexURLRequestMapper(
                new InmutableURLRequestMapper(
                        new InmutableURLResult()),
                Pattern.compile("^/([^/]+)/([^/]+)/([^/]+)/(.*)$"),
                        "$1-$2-$3/$4");
        
        final MockHttpServletRequest request = new MockHttpServletRequest(
                "GET", "/zauber/code/releases/foo/hola");
        assertFalse(r.getProxiedURLFromRequest(request).hasResult());
    }
    
    /**  @throws MalformedURLException */
    public final void testNoMatch() throws MalformedURLException {
        final URLRequestMapper r =  new RegexURLRequestMapper(
                new InmutableURLRequestMapper(
                        new InmutableURLResult(new URL(
                         "http://localhost:9095/nexus/content/repositories/"))),
                Pattern.compile("^/([^/]+)/([^/]+)/([^/]+)/(.*)$"),
                        "$1-$2-$3/$4");
        
        final MockHttpServletRequest request = new MockHttpServletRequest(
                "GET", "/zauber");
        assertFalse(r.getProxiedURLFromRequest(request).hasResult());
    }
    /**  @throws MalformedURLException */
    public final void testToString() throws MalformedURLException {
        final URLRequestMapper r =  new RegexURLRequestMapper(
                Pattern.compile("^/([^/]+)/([^/]+)/([^/]+)/(.*)$"),
               "http://localhost:9095/nexus/content/repositories/$1-$2-$3/$4");
        
        assertEquals("^/([^/]+)/([^/]+)/([^/]+)/(.*)$="
                + "http://localhost:9095/nexus/content/repositories/$1-$2-$3/$4",
                r.toString());
    }
}

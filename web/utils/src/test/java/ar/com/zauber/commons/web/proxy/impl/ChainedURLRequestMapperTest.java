/*
 * Copyright (c) 2005-2008 Zauber S.A. <http://www.zauber.com.ar/>
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
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;

import ar.com.zauber.commons.web.proxy.URLRequestMapper;

/**
 * Tests for {@link ChainedURLRequestMapper}
 * 
 * 
 * @author Juan F. Codagnone
 * @since Aug 29, 2008
 */
public class ChainedURLRequestMapperTest extends TestCase {

    /** test */
    public final void testEmpty()  {
        final URLRequestMapper c = new ChainedURLRequestMapper(
                Collections.EMPTY_LIST);
        c.getProxiedURLFromRequest(new MockHttpServletRequest());
    }
    

    /** test */
    public final void testNullArguments()  {
        try {
            new ChainedURLRequestMapper(Arrays.asList(new URLRequestMapper[]{
                new InmutableURLRequestMapper(new InmutableURLResult()),
                null,
            }));
            fail();
        } catch(final IllegalArgumentException e) {
            // ok
        }
    }
    
    /**   @throws MalformedURLException on test */
    public final void testChain() throws MalformedURLException  {
        final URLRequestMapper c = new ChainedURLRequestMapper(
            Arrays.asList(new URLRequestMapper[] {
                new RegexURLRequestMapper(
                        new InmutableURLRequestMapper(
                                new InmutableURLResult(new URL(
                         "http://localhost:9095/nexus/"))),
                        Pattern.compile("^/nexus/(.*)$"),
                        "$1"),
                new RegexURLRequestMapper(
                        new InmutableURLRequestMapper(
                                new InmutableURLResult(new URL(
                         "http://localhost:9095/nexus/content/repositories/"))),
                        Pattern.compile("^/([^/]+)/([^/]+)/([^/]+)/(.*)$"),
                        "$1-$2-$3/$4"),
        }));
        
       assertFalse(c.getProxiedURLFromRequest(new MockHttpServletRequest(
               "GET", "/foo")).hasResult());
       assertEquals(new URL("http://localhost:9095/nexus/foo/bar"), 
               c.getProxiedURLFromRequest(new MockHttpServletRequest(
               "GET", "/nexus/foo/bar")).getURL());
       assertEquals(new URL("http://localhost:9095/nexus/content/repositories/"
               + "zauber-code-releases/foo/bar"), 
               c.getProxiedURLFromRequest(new MockHttpServletRequest(
               "GET", "/zauber/code/releases/foo/bar")).getURL()); 
    }

    /**   @throws MalformedURLException on test */
    public final void testProperties() throws MalformedURLException  {
        final Properties properties = new Properties();
        properties.put("^/nexus/(.*)$", "http://localhost:9095/nexus/$1");
        properties.put("^/([^/]+)/([^/]+)/([^/]+)/(.*)$",
              "http://localhost:9095/nexus/content/repositories/$1-$2-$3/$4");
       final URLRequestMapper c = new ChainedURLRequestMapper(properties);
       
       assertFalse(c.getProxiedURLFromRequest(new MockHttpServletRequest(
               "GET", "/foo")).hasResult());
       assertEquals(new URL("http://localhost:9095/nexus/foo/bar"), 
               c.getProxiedURLFromRequest(new MockHttpServletRequest(
               "GET", "/nexus/foo/bar")).getURL());
       assertEquals(new URL("http://localhost:9095/nexus/content/repositories/"
               + "zauber-code-releases/foo/bar"), 
               c.getProxiedURLFromRequest(new MockHttpServletRequest(
               "GET", "/zauber/code/releases/foo/bar")).getURL()); 
    }
}

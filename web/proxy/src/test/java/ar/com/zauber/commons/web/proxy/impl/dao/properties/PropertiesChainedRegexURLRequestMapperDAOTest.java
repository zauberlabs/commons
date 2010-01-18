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
package ar.com.zauber.commons.web.proxy.impl.dao.properties;

import java.net.URL;
import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;

import ar.com.zauber.commons.web.proxy.URLRequestMapper;
import ar.com.zauber.commons.web.proxy.impl.ChainedURLRequestMapper;
import ar.com.zauber.commons.web.proxy.impl.RegexURLRequestMapper;
import ar.com.zauber.commons.web.proxy.impl.dao.properties.persister.NullPropertiesPersister;
import ar.com.zauber.commons.web.proxy.impl.dao.properties.provider.SimplePropertiesProvider;

/**
 * Tests {@link PropertiesChainedRegexURLRequestMapperDAO}.
 * 
 * @author Juan F. Codagnone
 * @since Aug 29, 2008
 */
public class PropertiesChainedRegexURLRequestMapperDAOTest extends TestCase {

    /** @throws Exception on error */
    public final void testLoad() throws Exception {
        final Properties p = new Properties();
        p.put("0", "^/nexus/(.*)$=http://localhost:9095/nexus/$1");
        p.put("1", "^/([^/]+)/([^/]+)/([^/]+)/(.*)$"
              + "=http://localhost:9095/nexus/content/repositories/$1-$2-$3/$4");
        
        final PropertiesChainedRegexURLRequestMapperDAO dao = 
            new PropertiesChainedRegexURLRequestMapperDAO(
                new SimplePropertiesProvider(p), 
                new NullPropertiesPersister());
        
        final URLRequestMapper c =  dao.load();
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

    /** @throws Exception on error */
    public final void testSave() throws Exception {
        final URLRequestMapper c =  new ChainedURLRequestMapper(Arrays.asList(
            new URLRequestMapper[] {
                new RegexURLRequestMapper(
                        Pattern.compile("^/nexus/(.*)$"),
                        "http://localhost:9095/nexus/$1"),
                new RegexURLRequestMapper(
                        Pattern.compile("^/([^/]+)/([^/]+)/([^/]+)/(.*)$"),
                "http://localhost:9095/nexus/content/repositories/$1-$2-$3/$4"),
                        
        }));
        new PropertiesChainedRegexURLRequestMapperDAO(
            new SimplePropertiesProvider(), 
            new PropertiesPersister() {
                public void save(final Properties properties) {
                    assertEquals(2, properties.size());
                    assertEquals("^/nexus/(.*)$=http://localhost:9095/nexus/$1",
                            properties.get("0"));
                    assertEquals("^/([^/]+)/([^/]+)/([^/]+)/(.*)$=http://"
                      + "localhost:9095/nexus/content/repositories/$1-$2-$3/$4",
                      properties.get("1"));
                }
            }).save(c);
    }
    

    /** test */
    public final void testLoadNoStrip() {
        final Properties p = new Properties();
        p.put("0", "^/nexus/(.*)$=http://localhost:9095/nexus/$1");
        
        final PropertiesChainedRegexURLRequestMapperDAO dao = 
            new PropertiesChainedRegexURLRequestMapperDAO(
                new SimplePropertiesProvider(p), 
                new NullPropertiesPersister());
      dao.setStripContextPath(false);
      dao.setStripServletPath(false);
      final ChainedURLRequestMapper c = (ChainedURLRequestMapper) dao.load();
      RegexURLRequestMapper r = (RegexURLRequestMapper) c.getChain()[0];
      assertFalse(r.isStripServletPath());

    }
}

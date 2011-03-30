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
package ar.com.zauber.commons.web.proxy.impl.dao.behaviour;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;

import ar.com.zauber.commons.web.proxy.URLRequestMapper;
import ar.com.zauber.commons.web.proxy.impl.InmutableURLRequestMapper;
import ar.com.zauber.commons.web.proxy.impl.InmutableURLResult;
import ar.com.zauber.commons.web.proxy.impl.dao.URLRequestMapperDAO;
import ar.com.zauber.commons.web.proxy.impl.dao.behaviour.impl.FalseURLRequestMapperAssertion;
import ar.com.zauber.commons.web.proxy.impl.dao.behaviour.impl.InmutableURLRequestMapperAssertion;
import ar.com.zauber.commons.web.proxy.impl.dao.behaviour.impl.MapURLRequestMapperAssertionsDAO;

/**
 * Tests {@link AssertURLRequestMapperDAO}.
 * 
 * @author Juan F. Codagnone
 * @since Aug 30, 2008
 */
public class AssertURLRequestMapperDAOTest extends TestCase {

    /** @throws MalformedURLException on error  
     * @throws URLRequestMapperAssertionsException on error */
    public final void testFailSave() throws URLRequestMapperAssertionsException, 
                                            MalformedURLException {
        try {
            new AssertURLRequestMapperDAO(new URLRequestMapperDAO() {
                public URLRequestMapper load() {
                    fail();
                    return null;
                }
                public void save(final URLRequestMapper urlRequestMapper) {
                    fail();
                }
            }, new MapURLRequestMapperAssertionsDAO(
                Arrays.asList(new URLRequestMapperAssertion[] {
                    new InmutableURLRequestMapperAssertion(0L,
                        new InmutableURLResult(new URL("http://localhost")),
                        new MockHttpServletRequest()),
                    new FalseURLRequestMapperAssertion(),
                }))).save(new InmutableURLRequestMapper(new InmutableURLResult()));
            fail();
        } catch(final URLRequestMapperAssertionsException e) {
            // ok
        }
    }
    
    /** test */
    public final void testSave() {
        final List<Integer> l = new ArrayList<Integer>();
        new AssertURLRequestMapperDAO(new URLRequestMapperDAO() {
            public URLRequestMapper load() {
                fail();
                return null;
            }
            public void save(final URLRequestMapper urlRequestMapper) {
                l.add(1);
            }
        }, new MapURLRequestMapperAssertionsDAO(
            Arrays.asList(new URLRequestMapperAssertion[] {
                new InmutableURLRequestMapperAssertion(0L,
                    new InmutableURLResult(),
                    new MockHttpServletRequest()),
            }))).save(new InmutableURLRequestMapper(new InmutableURLResult()));
        
        assertEquals(1, l.size());
    }
}

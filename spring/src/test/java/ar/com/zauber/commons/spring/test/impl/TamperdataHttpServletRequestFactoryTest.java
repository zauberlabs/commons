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
package ar.com.zauber.commons.spring.test.impl;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;

import ar.com.zauber.commons.spring.test.HttpServletRequestFactory;

/**
 * Test for {@link TamperdataHttpServletRequestFactory}.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Apr 22, 2008
 */
public class TamperdataHttpServletRequestFactoryTest extends TestCase {

    /** testea */
    public final void testFoo() {
        final HttpServletRequestFactory f = 
            new TamperdataHttpServletRequestFactory();
        
        final MockHttpServletRequest request = (MockHttpServletRequest) 
            f.create(getClass().getClassLoader().getResourceAsStream(
                "ar/com/zauber/commons/spring/test/impl/tamperdata-0.xml"));
        
        assertEquals("gzip,deflate", request.getHeader("Accept-Encoding"));
        assertEquals("carlos", request.getParameter("username"));
        assertEquals("POST", request.getMethod());
    }
}

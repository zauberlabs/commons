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
package ar.com.zauber.commons.web.proxy.impl.dao.behaviour.impl;

import ar.com.zauber.commons.web.proxy.impl.InmutableURLRequestMapper;
import ar.com.zauber.commons.web.proxy.impl.InmutableURLResult;
import ar.com.zauber.commons.web.proxy.impl.dao.behaviour.URLRequestMapperAssertionsException;
import junit.framework.TestCase;

/**
 * Tests {@link FalseURLRequestMapperAssertion}.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Aug 30, 2008
 */
public class FalseURLRequestMapperAssertionTest extends TestCase {

    /** test */
    public final void testFail() {
        try {
            new FalseURLRequestMapperAssertion().assertRequest(
                    new InmutableURLRequestMapper(new InmutableURLResult()));
            fail();
        } catch(URLRequestMapperAssertionsException e) {
            // ok
        }
    }
}

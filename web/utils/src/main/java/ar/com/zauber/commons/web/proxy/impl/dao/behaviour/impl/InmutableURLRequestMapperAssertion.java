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

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.proxy.URLRequestMapper;
import ar.com.zauber.commons.web.proxy.URLResult;
import ar.com.zauber.commons.web.proxy.impl.dao.behaviour.URLRequestMapperAssertion;
import ar.com.zauber.commons.web.proxy.impl.dao.behaviour.URLRequestMapperAssertionsException;

/**
 * Inmutable {@link URLRequestMapperAssertion}.
 * 
 * @author Juan F. Codagnone
 * @since Aug 30, 2008
 */
public class InmutableURLRequestMapperAssertion 
  implements URLRequestMapperAssertion {
    private final URLResult expectedResult;
    private final HttpServletRequest testRequest;
    
    /** constructor */
    public InmutableURLRequestMapperAssertion(final URLResult expectedResult,
            final HttpServletRequest testRequest) {
        Validate.notNull(expectedResult);
        Validate.notNull(testRequest);
        
        this.expectedResult = expectedResult;
        this.testRequest = testRequest;
    }
    
    /** @see URLRequestMapperAssertion#assertRequest(URLRequestMapper) */
    public final void assertRequest(final URLRequestMapper urlRequestMapper) {
        final URLResult r =  urlRequestMapper.getProxiedURLFromRequest(testRequest);
        if(!expectedResult.equals(r)) {
            throw new URLRequestMapperAssertionsException(this);
        }
    }

    /** @see URLRequestMapperAssertion#getExpectedResult() */
    public final URLResult getExpectedResult() {
        return expectedResult;
    }
    
    /** @see URLRequestMapperAssertion#getTestRequest() */
    public final HttpServletRequest getTestRequest() {
        return testRequest;
    }
}

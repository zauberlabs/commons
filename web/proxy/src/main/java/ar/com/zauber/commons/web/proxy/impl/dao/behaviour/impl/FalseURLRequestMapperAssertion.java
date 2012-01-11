/**
 * Copyright (c) 2005-2012 Zauber S.A. <http://www.zaubersoftware.com/>
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

import org.apache.commons.lang.NotImplementedException;

import ar.com.zauber.commons.web.proxy.URLRequestMapper;
import ar.com.zauber.commons.web.proxy.URLResult;
import ar.com.zauber.commons.web.proxy.impl.dao.behaviour.URLRequestMapperAssertion;
import ar.com.zauber.commons.web.proxy.impl.dao.behaviour.URLRequestMapperAssertionsException;

/**
 * {@link URLRequestMapper} that always fails.
 * 
 * @author Juan F. Codagnone
 * @since Aug 30, 2008
 */
public class FalseURLRequestMapperAssertion implements URLRequestMapperAssertion {

    /** @see URLRequestMapperAssertion#assertRequest(URLRequestMapper) */
    public final void assertRequest(final URLRequestMapper urlRequestMapper) {
        throw new URLRequestMapperAssertionsException(this);
        
    }

    /** @see URLRequestMapperAssertion#getExpectedResult() */
    public final URLResult getExpectedResult() {
        throw new NotImplementedException("won't be implemented");
    }

    /** @see URLRequestMapperAssertion#getTestRequest() */
    public final HttpServletRequest getTestRequest() {
        throw new NotImplementedException("won't be implemented");
    }

    /** @see URLRequestMapperAssertion#getId() */
    public final long getId() {
        return 0;
    }
}

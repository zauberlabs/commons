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
package ar.com.zauber.commons.web.proxy.impl.dao.behaviour;

import javax.servlet.http.HttpServletRequest;

import ar.com.zauber.commons.web.proxy.URLRequestMapper;
import ar.com.zauber.commons.web.proxy.URLResult;

/**
 * {@link URLRequestMapper} assertions. Used to tests the configuration
 * of {@link URLRequestMapper}s.
 * 
 * @author Juan F. Codagnone
 * @since Aug 30, 2008
 */
public interface URLRequestMapperAssertion {

    /** identify */
    long getId();
    
    /** @return the request used to test */
    HttpServletRequest getTestRequest();
    
    /** @return the expected result */
    URLResult getExpectedResult();
    
    /** 
     * asserts the assertion against an {@link URLRequestMapper}.
     * @throws URLRequestMapperAssertionsException if fails to assert 
     */
    void assertRequest(URLRequestMapper urlRequestMapper) 
        throws URLRequestMapperAssertionsException;
}

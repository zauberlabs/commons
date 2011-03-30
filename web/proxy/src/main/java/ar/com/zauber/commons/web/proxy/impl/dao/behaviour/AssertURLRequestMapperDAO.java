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

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.proxy.URLRequestMapper;
import ar.com.zauber.commons.web.proxy.impl.dao.URLRequestMapperDAO;

/**
 * {@link URLRequestMapperDAO} that runs the {@link URLRequestMapper} that
 * is being saved against a list of assertions. If one of the assertions
 * fails, the {@link URLRequestMapper} isn't saved, and a 
 * {@link URLRequestMapperAssertionsException} is thrown with the offending 
 * assertion.
 * MutableDelegateURLRequestMapperDAO
 * @author Juan F. Codagnone
 * @since Aug 30, 2008
 */
public class AssertURLRequestMapperDAO implements URLRequestMapperDAO {
    private final URLRequestMapperDAO target;
    private final URLRequestMapperAssertionsDAO assertionsDAO;

    /**
     * Creates the AssertURLRequestMapperDAO.
     *
     * @param target         target DAO
     * @param assertionsDAO  assertions DAO
     */
    public AssertURLRequestMapperDAO(final URLRequestMapperDAO target,
            final URLRequestMapperAssertionsDAO assertionsDAO) {
        Validate.notNull(target);
        Validate.notNull(assertionsDAO);
        
        this.target = target;
        this.assertionsDAO = assertionsDAO;
    }
    
    /** @see URLRequestMapperDAO#load() */
    public final URLRequestMapper load() {
        return target.load();
    }

    /** 
     * @see URLRequestMapperDAO#save(URLRequestMapper)
     * @throws URLRequestMapperAssertionsException of assertion fail 
     */
    public final void save(final URLRequestMapper urlRequestMapper) 
     throws URLRequestMapperAssertionsException {
        Validate.notNull(urlRequestMapper);
        
        for(final URLRequestMapperAssertion assertion 
            : assertionsDAO.getAssertions()) {
            assertion.assertRequest(urlRequestMapper);
        }
        target.save(urlRequestMapper);
    }
}

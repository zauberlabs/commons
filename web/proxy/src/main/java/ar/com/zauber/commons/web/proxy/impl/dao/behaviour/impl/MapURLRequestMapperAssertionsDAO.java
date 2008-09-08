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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.exception.DuplicatedEntityException;
import ar.com.zauber.commons.dao.exception.NoSuchEntityException;
import ar.com.zauber.commons.web.proxy.impl.dao.behaviour.URLRequestMapperAssertion;
import ar.com.zauber.commons.web.proxy.impl.dao.behaviour.URLRequestMapperAssertionsDAO;

/**
 * Inmutable {@link URLRequestMapperAssertionsDAO}. Always returns 
 * the sames assertions. 
 * 
 * @author Juan F. Codagnone
 * @since Aug 30, 2008
 */
public class MapURLRequestMapperAssertionsDAO 
  implements URLRequestMapperAssertionsDAO {
    private final Map<Long, URLRequestMapperAssertion> assertions = 
        new HashMap<Long, URLRequestMapperAssertion>();
    private long ids = 0;
    
    /** constructor */
    public MapURLRequestMapperAssertionsDAO() {
        // default
    }
    
    /** constructor */
    public MapURLRequestMapperAssertionsDAO(
            final List<URLRequestMapperAssertion> assertions) {
        Validate.noNullElements(assertions);
        
        for(URLRequestMapperAssertion assertion : assertions) {
            Validate.notNull(assertion);
            this.assertions.put(assertion.getId(), assertion);
        }
    }
    /** @see URLRequestMapperAssertionsDAO#getAssertions() */
    public final List<URLRequestMapperAssertion> getAssertions() {
        return new ArrayList<URLRequestMapperAssertion>(assertions.values());
    }
    
    /** @see URLRequestMapperAssertionsDAO#create(RequestMapperAssertion) */
    public final long create(final URLRequestMapperAssertion e)
            throws DuplicatedEntityException {
        Validate.notNull(e);
        
        final long ret = ids;
        assertions.put(ids++, e);
        return ret;
    }
    
    /** @see URLRequestMapperAssertionsDAO#delete(long) */
    public final void delete(final long id) throws NoSuchEntityException {
        if(assertions.containsKey(id)) {
            assertions.remove(id);
        } else {
            throw new NoSuchEntityException(id);
        }
    }
    /** @see URLRequestMapperAssertionsDAO#get(long) */
    public final URLRequestMapperAssertion get(final long id) 
        throws NoSuchEntityException {
        if(assertions.containsKey(id)) {
            return assertions.get(id);
        } else {
            throw new NoSuchEntityException(id); 
        }
    }
}

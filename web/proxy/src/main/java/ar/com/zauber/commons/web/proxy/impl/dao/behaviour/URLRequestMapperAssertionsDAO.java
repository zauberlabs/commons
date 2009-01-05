/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
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

import java.util.List;

import ar.com.zauber.commons.dao.exception.DuplicatedEntityException;
import ar.com.zauber.commons.dao.exception.NoSuchEntityException;

/**
 * DAO for {@link URLRequestMapperAssertion}.
 * 
 * @author Juan F. Codagnone
 * @since Aug 30, 2008
 */
public interface URLRequestMapperAssertionsDAO {
    
    /** @return the assertions */
    List<URLRequestMapperAssertion> getAssertions();
    
    /**
     * saves the assertion. 
     * @throws DuplicatedEntityException if the entity does no exists
     * @return the assertion id.  
     */
    long create(URLRequestMapperAssertion e) throws DuplicatedEntityException;
    
    /** 
     * @returns an assertion by id 
     * @throws NoSuchEntityException if the entity does no exists
     */
    URLRequestMapperAssertion get(long id) throws NoSuchEntityException;
    
    /** 
     * deletes an assertion by id 
     * @throws NoSuchEntityException if the entity does no exists
     */
    void delete(long id) throws NoSuchEntityException;
}

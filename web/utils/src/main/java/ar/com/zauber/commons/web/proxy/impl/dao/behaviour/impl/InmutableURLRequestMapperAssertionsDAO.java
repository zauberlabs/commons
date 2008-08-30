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

import java.util.List;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.proxy.impl.dao.behaviour.URLRequestMapperAssertion;
import ar.com.zauber.commons.web.proxy.impl.dao.behaviour.URLRequestMapperAssertionsDAO;

/**
 * Inmutable {@link URLRequestMapperAssertionsDAO}. Always returns 
 * the sames assertions. 
 * 
 * @author Juan F. Codagnone
 * @since Aug 30, 2008
 */
public class InmutableURLRequestMapperAssertionsDAO 
  implements URLRequestMapperAssertionsDAO {
    private final List<URLRequestMapperAssertion> assertions;
    
    /** constructor */
    public InmutableURLRequestMapperAssertionsDAO(
            final List<URLRequestMapperAssertion> assertions) {
        Validate.noNullElements(assertions);
        
        this.assertions = assertions;
    }
    /** @see URLRequestMapperAssertionsDAO#getAssertions() */
    public final List<URLRequestMapperAssertion> getAssertions() {
        return assertions;
    }
}

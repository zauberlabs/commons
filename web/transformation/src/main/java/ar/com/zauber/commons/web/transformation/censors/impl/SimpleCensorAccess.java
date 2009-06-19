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
package ar.com.zauber.commons.web.transformation.censors.impl;

import java.util.List;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.transformation.censors.CensorAccess;

/**
 * Implementación tonta de {@link CensorAccess} que recibe un listado
 * fijo de uris para censurar.
 * 
 * @author Matías Tito
 * @since Oct 22, 2008
 */
public class SimpleCensorAccess extends AbstractCensorAccess  {
    private final List<String> forbidenURIs;

    /** Creates the SimpleCensorAccess. */
    public SimpleCensorAccess(final List<String> forbidenURIs) {
        Validate.noNullElements(forbidenURIs);
        
        this.forbidenURIs = forbidenURIs;
    }
    
    /** @see CensorAccess#canAccess(java.lang.String) */
    public final boolean canAccess(final String uri) {
        validate(uri);
        boolean ret = true;
        
        for(final String forbidenURI : forbidenURIs) {            
            if (forbidenURI.equals(uri)) {
                ret = false;
            }
        }
        
        return ret;
    }
}

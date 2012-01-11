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
package ar.com.zauber.commons.web.proxy.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.proxy.URLRequestMapper;
import ar.com.zauber.commons.web.proxy.URLResult;

/**
 * Mutable {@link URLRequestMapper}.  Delegates the behaviour on a target
 * {@link URLRequestMapper}. The target {@link URLRequestMapper} can be changed
 * once this class is initialized.
 * 
 * @author Juan F. Codagnone
 * @since Aug 29, 2008
 */
public class MutableURLRequestMapper implements URLRequestMapper {
    private URLRequestMapper target;
    
    /** default constructor */
    public MutableURLRequestMapper() {
        // ok
    }
    
    /** default constructor */
    public MutableURLRequestMapper(final URLRequestMapper initialRequestMapper) {
        Validate.notNull(initialRequestMapper);
        
        target = initialRequestMapper;
    }
    
    /** @see URLRequestMapper#getProxiedURLFromRequest(HttpServletRequest) */
    public final URLResult getProxiedURLFromRequest(
            final HttpServletRequest request) {
        final URLResult result;
        if(target == null) {
            result = new InmutableURLResult();
        } else {
            result = target.getProxiedURLFromRequest(request);
        }
        
        return result;
    }

    /**
     * Sets the target. 
     *
     * @param target <code>URLRequestMapper</code> with the target.
     */
    public final void setTarget(final URLRequestMapper target) {
        this.target = target;
    }

    public final URLRequestMapper getTarget() {
        return target;
    }
}

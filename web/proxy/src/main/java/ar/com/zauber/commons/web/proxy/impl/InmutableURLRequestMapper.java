/**
 * Copyright (c) 2005-2015 Zauber S.A. <http://flowics.com/>
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
 * {@link URLRequestMapper} that always return the same url independely 
 * of the request.
 * 
 * @author Juan F. Codagnone
 * @since Aug 28, 2008
 */
public class InmutableURLRequestMapper implements URLRequestMapper {
    private URLResult result;
    
    /** constructor */
    public InmutableURLRequestMapper(final URLResult result) {
        Validate.notNull(result);
        
        this.result = result;
    }
    /** @see URLRequestMapper#getProxiedURLFromRequest(HttpServletRequest) */
    public final URLResult getProxiedURLFromRequest(
            final HttpServletRequest request) {
        return result;
    }
}

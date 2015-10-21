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
package ar.com.zauber.commons.web.uri.factory;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.Validate;

/**
 * Implementacion util para tests
 * 
 * @author Juan F. Codagnone
 * @since Sep 4, 2010
 */
public class MutableRequestProvider implements RequestProvider {
    private HttpServletRequest request;
    
    /** construct */
    public MutableRequestProvider() {
        // void
    }
    
    /** construct */
    public MutableRequestProvider(final HttpServletRequest request) {
        Validate.notNull(request);
        
        this.request = request;
    }
    
    /** @see RequestProvider#getRequest() */
    public final HttpServletRequest getRequest() {
        if(request == null) {
            throw new IllegalStateException("no request set");
        }
        return request;
    }

    public final void setRequest(final HttpServletRequest request) {
        this.request = request;
    }
}

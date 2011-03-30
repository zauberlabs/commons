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
package ar.com.zauber.commons.web.filter.webkit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 *  Clase que extiende el wrapper de {@link HttpServletRequest} para cambiar el 
 *  Accept de content type a "text/html,application/xhtml+xml,
 *      application/xml;q=0.9,*\/*;q=0.8".
 * @author Mariano Semelman
 * @since Jun 11, 2010
 */
public class WebkitHttpServletRequestWrapper extends HttpServletRequestWrapper {

    /** Creates the WebkitHttpServletRequestWrapper.
     * @param request */
    public WebkitHttpServletRequestWrapper(final HttpServletRequest request) {
        super(request);
    }

    //CHECKSTYLE:ALL:OFF
    private static final String ACCEPT = "Accept";
    private static final String ACCEPT_HEADER = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
    //CHECKSTYLE:ALL:ON
    
    /** @see HttpServletRequestWrapper#getHeader(String) */
    @Override
    public final String getHeader(final String name) {
        if(name != null && name.equals(ACCEPT)) {
            return ACCEPT_HEADER;
        }
        return super.getHeader(name);
    }
    
}

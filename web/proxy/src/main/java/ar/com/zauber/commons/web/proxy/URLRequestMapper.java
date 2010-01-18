/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
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
package ar.com.zauber.commons.web.proxy;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Given an HTTP request, the responsability of this class is to 
 * return an {@link URL} where the resource that can fullfill the
 * request can be found.
 * </p><p>
 *  For example a request with the URI 
 *     /zauber/code/snapshots/ar/com/zauber/maven/pom.xml
 *  could be mapped to the url
 *    http://localhost:9095/nexus/content/repositories/zauber-code-snapshots/
 *      ar/com/zauber/maven/pom.xml 
 * </p>
 * 
 * @author Juan F. Codagnone
 * @since Aug 28, 2008
 */
public interface URLRequestMapper {

    
    /**
     * @return the {@link URLResult} that may fullfill the request.  
     * Never returns null.
     */
    URLResult getProxiedURLFromRequest(final HttpServletRequest request);
}

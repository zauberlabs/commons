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
package ar.com.zauber.commons.web.uri.factory;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * {@link RequestProvider} que obtiene el request desde spring
 * 
 * @author Juan F. Codagnone
 * @since Sep 4, 2010
 */
public class ContextListenerRequestProvider implements RequestProvider {

    /** @see RequestProvider#getRequest() */
    public final HttpServletRequest getRequest() {
        final RequestAttributes attr = RequestContextHolder.getRequestAttributes();
        if(attr instanceof ServletRequestAttributes) {
            final ServletRequestAttributes r = (ServletRequestAttributes) attr;
            return r.getRequest();
        }
        throw new IllegalStateException("unknown class " 
                + ServletRequestAttributes.class.getName());
    }
}

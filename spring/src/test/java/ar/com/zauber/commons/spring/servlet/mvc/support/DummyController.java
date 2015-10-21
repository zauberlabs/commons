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
package ar.com.zauber.commons.spring.servlet.mvc.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * Controlador para ser usado en {@link ZauberBeanNameBasedClassNameHandlerMapping}
 * 
 * @author Juan F. Codagnone
 * @since Dec 25, 2006
 */
public class DummyController implements Controller {
    /** id */
    private final String id;
    
    /**
     * Creates the DummyController.
     *
     * @param id id del controlador
     */
    public DummyController(final String id) {
        Validate.notNull(id);
        this.id = id;
    }
    
    /**  @see Controller#handleRequest(HttpServletRequest, 
     * HttpServletResponse) */
    public final ModelAndView handleRequest(final HttpServletRequest request, 
            final HttpServletResponse response) throws Exception {
        // nothing to do
        
        return null;
    }
    
    /**
     * Returns the id.
     * 
     * @return <code>String</code> with the id.
     */
    public final String getId() {
        return id;
    }

}

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
package ar.com.zauber.commons.spring.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import ar.com.zauber.commons.spring.web.SpringWebUtil;


/**
 * Controlador Util para poner a atender las url de controladores
 * que cambiaron su URI para siempre.
 * 
 * @author Juan F. Codagnone
 * @since Jan 2, 2007
 */
public abstract class AbstractLegacyRedirectController extends AbstractController {
    /** web util */
    private final SpringWebUtil webUtil;
    
    /**
     * Creates the AbstractLegacyRedirectController.
     *
     * @param webUtil webutil
     */
    public AbstractLegacyRedirectController(final SpringWebUtil webUtil) {
        Validate.notNull(webUtil);
        
        this.webUtil = webUtil;
    }
    
    
    /**
     * @param request http request
     * @return la nueva url a donde hay que redirigirse.
     */
    protected abstract String newURI(final HttpServletRequest request);
    
    /**  @see AbstractController#handleRequestInternal(HttpServletRequest, 
     *    HttpServletResponse)
     */
    @Override
    protected ModelAndView handleRequestInternal(
            final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        
        return new ModelAndView(
                webUtil.createPermanentlyRedirect(newURI(request)));
    }
}

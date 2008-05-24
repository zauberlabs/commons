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
package ar.com.zauber.commons.spring.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;


/**
 * Dummy controller that just returns a view, with out a model 
 *
 * @author Juan F. Codagnone
 * @since Sep 29, 2005
 */
public class NoModelController extends AbstractController {
    /** view name to return */
    private String view;
    /**
     * Creates the NoModelController.
     *
     * @param view name of the view to return
     */
    public NoModelController(final String view) {
        this.view = view;
    }
    
    /**     
     * @see AbstractController#handleRequestInternal(HttpServletRequest,
     *  HttpServletResponse)
     */
    @Override
    protected final ModelAndView handleRequestInternal(
           final HttpServletRequest request, final HttpServletResponse response)
           throws Exception {
        
        
        
        return new ModelAndView(view);
    }
}

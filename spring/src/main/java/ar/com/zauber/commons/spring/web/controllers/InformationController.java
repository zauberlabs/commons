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
package ar.com.zauber.commons.spring.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;


/**
 * Information
 * 
 * @author Juan F. Codagnone
 */
public class InformationController extends AbstractController {
    /** base view */
    private final String baseView;

    /**
     * Creates the InformationController.
     *
     * @param baseView base view
     */
    public InformationController (final String baseView) {
        Validate.notNull(baseView);

        this.baseView = baseView;
    }

    /**
     * @see AbstractController#handleRequest(HttpServletRequest,
     *      HttpServletResponse)
     */
    @Override
    protected final ModelAndView handleRequestInternal(
            final HttpServletRequest request, 
            final HttpServletResponse response)
            throws Exception {
        
        final String uri = request.getRequestURI().substring(
                request.getContextPath().length());
        
        if(uri.contains("..")) {
            throw new IllegalArgumentException("..");
        }
        final int i = uri.lastIndexOf('.');
        String view = (i == -1) ? uri : uri.substring(1, i);
        while(view.startsWith("/")) {
            view = view.substring(1);
        }
        while(view.endsWith("/")) {
            view = view.substring(0, view.length() - 1);
        }
        
        final Resource r = getApplicationContext().getResource(
                "/WEB-INF/templates/jsp/" + baseView + view + ".jsp");
        final ModelAndView ret;
        if(r.exists()) {
            ret = new ModelAndView(baseView + view);
        } else {
            response.sendError(404);
            ret = null;
        }
        return ret;
    }
}
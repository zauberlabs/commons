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

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * Controlador que mapea condiciones de error 
 * 
 * @author Pablo Grigolatto
 * @since Jun 29, 2009
 */
public class ExceptionController extends AbstractController {
    private final Map<Integer, String> map;
    private final String defaultView;

    /** Creates the ExceptionController.*/
    public ExceptionController(final Map<Integer, String> map, 
            final String defaultView) {
        Validate.notNull(map);
        Validate.isTrue(StringUtils.isNotBlank(defaultView));
        this.map = map;
        this.defaultView = defaultView;
    }

    /** @see AbstractController#handleRequestInternal(
     * HttpServletRequest, HttpServletResponse) */
    @Override
    protected final ModelAndView handleRequestInternal(
            final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {

        final String view;
        final Integer statusCode = (Integer) 
            request.getAttribute("javax.servlet.error.status_code");
        
        if(map.containsKey(statusCode)) {
            view = map.get(statusCode);
        } else {
            view = this.defaultView;
        }

        return new ModelAndView(view);
    }

}

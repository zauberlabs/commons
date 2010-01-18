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
package ar.com.zauber.commons.facebook.utils.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import ar.com.zauber.commons.facebook.utils.impl.DefaultCallbackRequestValidator;

/**
 * Controlador que sabe atender pedidos de facebook. Separa el pedido en
 * request, response, y parametros de facebook
 * 
 * 
 * @author Marcelo Turrin
 * @since Mar 19, 2008
 */
public abstract class FacebookController extends AbstractController {
    private final String view;

    /**
     * Creates the FacebookController.
     *
     * @param view nombre de la vista de success
     */
    public FacebookController(final String view) {
        Validate.notEmpty(view);
        this.view = view;
    }

    /**
     * @see AbstractController#handleRequestInternal(HttpServletRequest,
     *      HttpServletResponse)
     */
    @Override
    protected final ModelAndView handleRequestInternal(
            final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        final Map<String, String> params = DefaultCallbackRequestValidator
                .extractFacebookParams(request);
        final ModelAndView ret = new ModelAndView(view);
        this.handleFacebookRequest(request, response, params, ret);

        return ret;
    }

    /** maneja el request */
    protected abstract void handleFacebookRequest(HttpServletRequest request,
            HttpServletResponse response, Map<String, String> params,
            ModelAndView ret) throws Exception;

}
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
package ar.com.zauber.commons.spring.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import ar.com.zauber.commons.web.proxy.HttpClientRequestProxy;

/**
 * AbstractProxyController that uses an {@link URLRequestMapper} to get
 * the destination URL.
 * 
 * @author Juan F. Codagnone
 * @since Aug 28, 2008
 */
public class ProxyController extends AbstractController {
    private final HttpClientRequestProxy httpClientRequestProxy;
    
    /** Creates the ProxyController. */
    public ProxyController(final HttpClientRequestProxy httpClientRequestProxy) {
        Validate.notNull(httpClientRequestProxy);
        
        this.httpClientRequestProxy = httpClientRequestProxy;
    }
    
    /** @see AbstractController#handleRequestInternal(HttpServletRequest, 
     * HttpServletResponse) */
    @Override
    protected final ModelAndView handleRequestInternal(
            final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        
        httpClientRequestProxy.handleRequestInternal(request, response);
        return null;
    }
}
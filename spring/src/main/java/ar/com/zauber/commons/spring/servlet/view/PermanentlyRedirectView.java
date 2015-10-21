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
package ar.com.zauber.commons.spring.servlet.view;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.RedirectView;


/**
 * <p>
 * Extension a {@link RedirectView} que permite enviar una respuesta
 * de tipo 301 (Moved Permanently) al cliente. Ver
 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html.
 * </p>
 * <p>
 * Util para cuando se cambia la uri de un controlador y se desea
 * que los clientes puedan seguir accediendo con la vieja uri, pero
 * se desea que los agents (google!) descarten la vieja url y tomen la
 * nueva.
 * </p>
 *
 * @author Juan F. Codagnone
 * @since Jan 2, 2007
 */
public class PermanentlyRedirectView extends RedirectView {

    /** constructor */
    public PermanentlyRedirectView() {
        // nothing to do
    }

    /** constructor */
    public PermanentlyRedirectView(final String url) {
        super(url);
    }

    /** @see RedirectView#sendRedirect() */
    @Override
    protected final void sendRedirect(final HttpServletRequest request, 
            final HttpServletResponse response, final String targetUrl, 
            final boolean http10Compatible)
            throws IOException {

        if(request.getMethod().toLowerCase().equals("get")) {
            response.setStatus(301);
            response.setHeader("Location", response.encodeRedirectURL(targetUrl));
        } else {
            super.sendRedirect(request, response, targetUrl, http10Compatible);
        }
    }
}

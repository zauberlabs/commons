/**
 * Copyright (c) 2005-2011 Zauber S.A. <http://www.zaubersoftware.com/>
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
package ar.com.zauber.commons.social.oauth.examples.services;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * This handler is called after the user logs in with OAuth.
 * 
 * 
 * @author Francisco J. González Costanzó
 * @since Sep 23, 2010
 */
public class ExampleAuthenticationSuccessHandler implements
        AuthenticationSuccessHandler {

    /**
     * @see AuthenticationSuccessHandler#onAuthenticationSuccess(HttpServletRequest,
     *      HttpServletResponse, Authentication)
     */
    @Override
    public final void onAuthenticationSuccess(final HttpServletRequest request,
            final HttpServletResponse response,
            final Authentication authentication) throws IOException,
            ServletException {

        ExampleUserDetails userDetails = (ExampleUserDetails) authentication
                .getPrincipal();

        if (userDetails.getUsername() == null) {
            // logged in with twitter, but no username for this application
            response.sendRedirect("/api/welcome/");
        } else {
            response.sendRedirect("/api/welcome/");
        }
    }

}

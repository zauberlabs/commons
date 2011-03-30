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
package ar.com.zauber.commons.social.facebook.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import ar.com.zauber.commons.social.facebook.FacebookCookie;
import ar.com.zauber.commons.social.facebook.FacebookCookieFinder;

/**
 * Authentication Processing Filter para login por Facebook.</br> 
 * </br> 
 * Este filtro debe capturar un callback llamado después de que el usuario se loguea
 * en FB usando la librería JavaScript de Facebook. Esta guarda los datos de la
 * sesión del usuario en una cookie, que es leída por este filtro para obtener
 * el Facebook UID.
 * 
 * @author Francisco J. González Costanzó
 * @since Feb 4, 2010
 */
public class FacebookAuthenticationProcessingFilter extends
        AbstractAuthenticationProcessingFilter {

    private final String facebookApiKey;

    /**
     * Creates the FacebookAuthenticationProcessingFilter.
     */
    protected FacebookAuthenticationProcessingFilter(final String callbackUri,
            final String facebookApiKey) {
        super(callbackUri);
        Validate.notNull(facebookApiKey);
        this.facebookApiKey = facebookApiKey;
    }

    /**
     * @see AbstractAuthenticationProcessingFilter
     *      #attemptAuthentication(HttpServletRequest,
     *      HttpServletResponse)
     */
    @Override
    public final Authentication attemptAuthentication(
            final HttpServletRequest request, final HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        if (!request.getMethod().equals("GET")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: "
                            + request.getMethod());
        }

        final FacebookCookie cookie = FacebookCookieFinder.getCookie(
                facebookApiKey, request);

        if (cookie == null) {
            throw new AuthenticationServiceException("not autenticated");
        } else {
            return this.getAuthenticationManager().authenticate(
                    new FacebookAuthenticationToken(cookie.getFbUID()));    
        }
    }

}

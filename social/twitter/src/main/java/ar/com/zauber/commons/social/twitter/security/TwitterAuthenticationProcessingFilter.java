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
package ar.com.zauber.commons.social.twitter.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

/**
 * Authentication Processing Filter for Twitter OAuth login.
 * <p>
 * This filter must capture the callback where the user is redirected to after
 * logging in.
 * 
 * @author Mariano Cortesi
 * @since Feb 3, 2010
 */
public class TwitterAuthenticationProcessingFilter extends
        AbstractAuthenticationProcessingFilter {

    /**
     * Creates the TwitterAuthenticationProcessingFilter.
     */
    protected TwitterAuthenticationProcessingFilter(
            final String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
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

        final String oauthToken = request.getParameter("oauth_token");
        final String oauthVerifier = request.getParameter("oauth_verifier");
        // verifier may be null
        final String denyToken = request.getParameter("denied");

        if (denyToken != null) {
            throw new BadCredentialsException("twitter access denied");
        }

        if (oauthToken == null) {
            throw new AuthenticationServiceException(
                    "missing oauth_token parameter");
        }

        return this.getAuthenticationManager().authenticate(
                new TwitterAuthenticationToken(oauthToken, oauthVerifier));
    }

}

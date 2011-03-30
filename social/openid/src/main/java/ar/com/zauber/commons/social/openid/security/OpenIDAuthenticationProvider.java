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
package ar.com.zauber.commons.social.openid.security;

import org.apache.commons.lang.Validate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import ar.com.zauber.commons.social.openid.OpenIDIdentity;

/**
 * {@link AuthenticationProvider} para OAuth de Twitter
 * 
 * @author Francisco J. González Costanzó
 * @since Feb 10, 2010
 */
public class OpenIDAuthenticationProvider implements AuthenticationProvider {

    private final OpenIDUserDetailsService userDetailsService;

    /**
     * Creates the OpenIDAuthenticationProvider.
     */
    public OpenIDAuthenticationProvider(
            final OpenIDUserDetailsService userDetailsService) {
        Validate.notNull(userDetailsService);
        this.userDetailsService = userDetailsService;
    }

    /** @see AuthenticationProvider#authenticate(Authentication) */
    public final Authentication authenticate(final Authentication authentication)
            throws AuthenticationException {
        Validate.notNull(authentication);
        OpenIDAuthenticationToken authenticationToken = 
            (OpenIDAuthenticationToken) authentication;

        OpenIDIdentity identity = (OpenIDIdentity) authenticationToken
                .getCredentials();

        UserDetails userDetails = this.userDetailsService
                .loadUserByOpenIDIdentity(identity);
        return new OpenIDAuthenticationToken(identity, userDetails, userDetails
                .getAuthorities());
    }

    /** @see AuthenticationProvider#supports(java.lang.Class) */
    public final boolean supports(final Class<? extends Object> authentication) {
        return (OpenIDAuthenticationToken.class
                .isAssignableFrom(authentication));
    }

}

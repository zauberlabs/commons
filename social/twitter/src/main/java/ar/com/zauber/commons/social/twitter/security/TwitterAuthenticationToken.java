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
package ar.com.zauber.commons.social.twitter.security;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import ar.com.zauber.commons.social.oauth.OAuthAccessToken;

/**
 * {@link Authentication} for Twitter OAuth.
 * 
 * @author Mariano Cortesi
 * @since Feb 3, 2010
 */
public class TwitterAuthenticationToken extends AbstractAuthenticationToken {

    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = -5033445678859869194L;
    
    private final Object principal;
    private final String oAuthToken;
    private final String oAuthVerifier;
    private final OAuthAccessToken accessToken;
    
    /** Crea un twitter authentication token, sin autenticar */
    public TwitterAuthenticationToken(final String oauthToken) {
        super(null);
        Validate.notNull(oauthToken);
        this.principal = null;
        this.oAuthToken = oauthToken;
        this.oAuthVerifier = null;
        this.accessToken = null;
        setAuthenticated(false);
    }
    
    /** Crea un twitter authentication token, sin autenticar */
    public TwitterAuthenticationToken(final String oauthToken,
            final String oauthVerifier) {
        super(null);
        Validate.notNull(oauthToken);
        this.principal = null;
        this.oAuthToken = oauthToken;
        this.oAuthVerifier = oauthVerifier;
        this.accessToken = null;
        setAuthenticated(false);
    }    

    /** Crea un twitter authentication token, autenticado */
    public TwitterAuthenticationToken(final OAuthAccessToken accessToken, 
            final Object principal,
            final Collection<GrantedAuthority> authorities) {
        super(authorities);
        this.oAuthToken = null;
        this.oAuthVerifier = null;
        this.accessToken = accessToken;
        this.principal = principal;
        super.setAuthenticated(true); // must use super, as we override
    }

    /** @return El accessToken del usuario */
    public final Object getCredentials() {
        return accessToken;
    }

    public final Object getPrincipal() {
        return principal;
    }
    
    public final String getOAuthToken() {
        return oAuthToken;
    }

    public final String getOAuthVerifier() {
        return oAuthVerifier;
    }

    /** @see AbstractAuthenticationToken#setAuthenticated(boolean) */
    @Override
    public final void setAuthenticated(final boolean isAuthenticated)
            throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - "
                    + "use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }

}

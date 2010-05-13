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
package ar.com.zauber.commons.social.openid.security;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import ar.com.zauber.commons.social.openid.OpenIDIdentity;

/**
 * {@link Authentication} para OpenID
 * 
 * @author Francisco J. González Costanzó
 * @since Feb 10, 2010
 */
public class OpenIDAuthenticationToken extends AbstractAuthenticationToken {

    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = 2467144508925499311L;

    private final Object principal;
    private final OpenIDIdentity identifier;

    /** Crea un openid authentication token, sin autenticar */
    public OpenIDAuthenticationToken(final OpenIDIdentity identifier) {
        super(null);
        Validate.notNull(identifier);
        this.principal = null;
        this.identifier = identifier;
        setAuthenticated(false);
    }
    
    /** Crea un openid authentication token, autenticado */
    public OpenIDAuthenticationToken(final OpenIDIdentity identifier, 
            final Object principal,
            final Collection<GrantedAuthority> authorities) {
        super(authorities);
        this.identifier = identifier;
        this.principal = principal;
        super.setAuthenticated(true); // must use super, as we override
    }

    /** @return el OpenID {@link Identifier} */
    public final Object getCredentials() {
        return identifier;
    }

    /** @see org.springframework.security.core.Authentication#getPrincipal() */
    public final Object getPrincipal() {
        return principal;
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

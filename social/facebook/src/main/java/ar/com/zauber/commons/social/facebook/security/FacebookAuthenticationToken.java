/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.social.facebook.security;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * {@link Authentication} para Facebook
 * 
 * @author Francisco J. González Costanzó
 * @since Feb 4, 2010
 */
public class FacebookAuthenticationToken extends AbstractAuthenticationToken {
    
    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = 4079523689407209313L;
    
    private final Object principal;
    private final Long facebookUID;
    
    /** Crea un facebook authentication token, sin autenticar */
    public FacebookAuthenticationToken(final Long fbUID) {
        super(null);
        Validate.notNull(fbUID);
        this.principal = fbUID;
        this.facebookUID = fbUID;
        setAuthenticated(false);
    }

    /** Crea un facebook authentication token autenticado */
    public FacebookAuthenticationToken(final Long fbUID,
            final Object principal,
            final Collection<GrantedAuthority> authorities) {
        super(authorities);
        this.facebookUID = fbUID;
        this.principal = principal;
        super.setAuthenticated(true); // must use super, as we override
    }

    /** @return El UID de Facebook del usuario */
    public final Object getCredentials() {
        return this.facebookUID;
    }

    public final Object getPrincipal() {
        return this.principal;
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
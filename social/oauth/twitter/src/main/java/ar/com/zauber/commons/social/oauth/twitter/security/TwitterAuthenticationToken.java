/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.social.oauth.twitter.security;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import ar.com.zauber.commons.social.oauth.OAuthAccessToken;

/**
 * {@link Authentication} para OAuth de Twitter
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

/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
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

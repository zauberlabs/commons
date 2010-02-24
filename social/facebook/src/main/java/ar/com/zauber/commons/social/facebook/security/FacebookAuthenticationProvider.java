/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.social.facebook.security;

import org.apache.commons.lang.Validate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * {@link AuthenticationProvider} para Facebook
 * 
 * @author Francisco J. González Costanzó
 * @since Feb 4, 2010
 */
public class FacebookAuthenticationProvider implements AuthenticationProvider {

    private final FacebookUserDetailsService userDetailsService;

    /**
     * Creates the FacebookAuthenticationProvider.
     */
    public FacebookAuthenticationProvider(
            final FacebookUserDetailsService userDetailsService) {
        Validate.notNull(userDetailsService);
        this.userDetailsService = userDetailsService;
    }

    /** @see AuthenticationProvider#authenticate(Authentication) */
    public final Authentication authenticate(final Authentication authentication)
            throws AuthenticationException {
        Validate.notNull(authentication);

        Long fbUID = (Long) authentication.getCredentials();
        UserDetails userDetails = this.userDetailsService
                .loadUserByFacebookUID(fbUID);
        return new FacebookAuthenticationToken(fbUID, userDetails, userDetails
                .getAuthorities());
    }

    /** @see AuthenticationProvider#supports(java.lang.Class) */
    public final boolean supports(final Class<? extends Object> authentication) {
        return (FacebookAuthenticationToken.class
                .isAssignableFrom(authentication));
    }

}

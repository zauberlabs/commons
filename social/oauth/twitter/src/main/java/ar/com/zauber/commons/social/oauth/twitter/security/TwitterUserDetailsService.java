/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.social.oauth.twitter.security;

import org.springframework.security.core.userdetails.UserDetails;

import ar.com.zauber.commons.social.oauth.OAuthAccessToken;

/**
 * Interfaz para UserDetailsService para usuarios de Twitter.
 * 
 * @author Francisco J. González Costanzó
 * @since Feb 24, 2010
 */
public interface TwitterUserDetailsService {

    /**
     * @param accessToken
     *            el accessToken correspondiente al usuario
     * @return el {@link UserDetails} del usuario correspondiente
     */
    UserDetails loadUserByTwitterAccessToken(OAuthAccessToken accessToken);

}

/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.social.openid.security;

import org.springframework.security.core.userdetails.UserDetails;

import ar.com.zauber.commons.social.openid.OpenIDIdentity;

/**
 * UserDetailsService para usuarios autenticados por OpenID
 * 
 * @author Francisco J. González Costanzó
 * @since Feb 24, 2010
 */
public interface OpenIDUserDetailsService {

    /**
     * @param identity
     *            open id autenticated identity
     * @return el {@link UserDetails} del usuario
     */
    UserDetails loadUserByOpenIDIdentity(OpenIDIdentity identity);

}

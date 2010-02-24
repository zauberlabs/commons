/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.social.facebook.security;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * UserDetailsService para usuarios de Facebook
 * 
 * @author Francisco J. González Costanzó
 * @since Feb 24, 2010
 */
public interface FacebookUserDetailsService {

    /**
     * @param fbUID
     *            Facebook ID
     * @return el {@link UserDetails} del usuario correspondiente al FacebookID
     */
    UserDetails loadUserByFacebookUID(Long fbUID);

}

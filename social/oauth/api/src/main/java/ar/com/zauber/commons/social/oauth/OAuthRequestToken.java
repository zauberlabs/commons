/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.social.oauth;

/**
 * RequestToken
 * 
 * @author Francisco J. Gonz�lez Costanz�
 * @since Jan 15, 2010
 */
public interface OAuthRequestToken {
    
    /** @return the token */
    String getToken();
    
    /** @return the token secret */
    String getTokenSecret();

}

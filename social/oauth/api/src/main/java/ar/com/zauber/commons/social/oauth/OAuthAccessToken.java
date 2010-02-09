/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.social.oauth;

/**
 * OAuth Access Token
 * 
 * @author Francisco J. González Costanzó
 * @since Jan 15, 2010
 */
public interface OAuthAccessToken {

    /** @return the token */
    String getToken();

    /** @return the token secret */
    String getTokenSecret();
    
    /** @return the screen name of the authenticated user */
    String getScreenName();
    
}

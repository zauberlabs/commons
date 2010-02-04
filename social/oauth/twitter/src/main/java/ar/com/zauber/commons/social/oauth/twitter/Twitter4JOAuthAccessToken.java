/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.social.oauth.twitter;

import org.apache.commons.lang.Validate;

import twitter4j.http.AccessToken;
import ar.com.zauber.commons.social.oauth.OAuthAccessToken;

/**
 * {@link OAuthAccessToken} que envuelve a la implementación de Twiiter4j
 * 
 * @author Francisco J. González Costanzó
 * @since Feb 4, 2010
 */
public class Twitter4JOAuthAccessToken implements OAuthAccessToken {
    
    private final AccessToken accessToken;

    /** Creates the Twitter4JOAuthAccessToken. */
    public Twitter4JOAuthAccessToken(AccessToken accessToken) {
        Validate.notNull(accessToken);
        this.accessToken = accessToken;
    }

    /** @see OAuthAccessToken#getToken() */
    public String getToken() {
        return accessToken.getToken();
    }

    /** @see OAuthAccessToken#getTokenSecret() */
    public String getTokenSecret() {
        return accessToken.getTokenSecret();
    }

}

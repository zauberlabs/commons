/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.social.oauth.twitter;

import java.io.Serializable;

import org.apache.commons.lang.Validate;

import twitter4j.http.AccessToken;
import ar.com.zauber.commons.social.oauth.OAuthAccessToken;

/**
 * {@link OAuthAccessToken} que envuelve a la implementación de Twiiter4j
 * 
 * @author Francisco J. González Costanzó
 * @since Feb 4, 2010
 */
public class Twitter4JOAuthAccessToken implements OAuthAccessToken,
        Serializable {
    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = 5018916311032391476L;
    
    private final AccessToken accessToken;

    /** Creates the Twitter4JOAuthAccessToken. */
    public Twitter4JOAuthAccessToken(final AccessToken accessToken) {
        Validate.notNull(accessToken);
        this.accessToken = accessToken;
    }

    /** @see OAuthAccessToken#getToken() */
    public final String getToken() {
        return accessToken.getToken();
    }

    /** @see OAuthAccessToken#getTokenSecret() */
    public final String getTokenSecret() {
        return accessToken.getTokenSecret();
    }

    /** @see OAuthAccessToken#getScreenName() */
    public final String getScreenName() {
        return accessToken.getScreenName();
    }

}

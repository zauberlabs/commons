/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.social.oauth.twitter;

import ar.com.zauber.commons.social.oauth.OAuthAccessToken;

/**
 * Implementación simple de {@link OAuthAccessToken}
 * 
 * @author Francisco J. González Costanzó
 * @since Jan 15, 2010
 */
public class OAuthAccessTokenImpl implements OAuthAccessToken {

    private final String token;
    private final String tokenSecret;

    /** Creates the OAuthAccessTokenImpl. */
    public OAuthAccessTokenImpl(final String token, final String tokenSecret) {
        this.token = token;
        this.tokenSecret = tokenSecret;
    }

    public final String getToken() {
        return token;
    }

    public final String getTokenSecret() {
        return tokenSecret;
    }
    
}

/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.social.oauth.twitter;

import ar.com.zauber.commons.social.oauth.OAuthRequestToken;

/**
 * Implementación simple de {@link OAuthRequestToken}
 * 
 * @author Francisco J. González Costanzó
 * @since Jan 15, 2010
 */
public class OAuthRequestTokenImpl implements OAuthRequestToken {

    private final String token;
    private final String tokenSecret;

    /** Creates the OAuthAccessTokenImpl. */
    public OAuthRequestTokenImpl(final String token, final String tokenSecret) {
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

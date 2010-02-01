/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.social.oauth.twitter;

import java.util.HashMap;
import java.util.Map;

import ar.com.zauber.commons.social.oauth.OAuthRequestToken;

/**
 * Clase de utilidad que almacena {@link OAuthRequestToken} bajo una key.
 * 
 * @author Francisco J. González Costanzó
 * @since Jan 15, 2010
 */
public final class OAuthRequestTokenProxy {

    private final Map<String, OAuthRequestToken> map = 
        new HashMap<String, OAuthRequestToken>();

    /**
     * @return el {@link OAuthRequestToken} que corresponde al oauthToken
     *         indicado
     */
    public OAuthRequestToken getRequestToken(final String key) {
        return map.get(key);
    }

    /***/
    public void put(final String key, final OAuthRequestToken token) {
        map.put(key, token);
    }
}

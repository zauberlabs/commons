/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.social.oauth.twitter;

import java.util.HashMap;
import java.util.Map;

import ar.com.zauber.commons.social.oauth.OAuthRequestToken;
import ar.com.zauber.commons.social.oauth.OAuthRequestTokenRepository;

/**
 * Implementación en memoria de {@link OAuthRequestTokenRepository}
 * 
 * @author Francisco J. González Costanzó
 * @since Feb 10, 2010
 */
public class MemoryOAuthRequestTokenRepository implements
        OAuthRequestTokenRepository {

    private final Map<String, OAuthRequestToken> map = 
        new HashMap<String, OAuthRequestToken>();

    /** @see OAuthRequestTokenRepository#get(java.lang.String) */
    public final OAuthRequestToken get(final String oauthToken) {
        return map.get(oauthToken);
    }

    /** @see OAuthRequestTokenRepository#remove(java.lang.String) */
    public final void remove(final String oauthToken) {
        map.remove(oauthToken);
    }

    /**
     * @see OAuthRequestTokenRepository#save(java.lang.String,
     *      OAuthRequestToken)
     */
    public final void save(final String oauthToken,
            final OAuthRequestToken requestToken) {
        map.put(oauthToken, requestToken);
    }

}

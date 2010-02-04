/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.social.oauth.twitter;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;

import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;
import ar.com.zauber.commons.social.oauth.OAuthAccessException;
import ar.com.zauber.commons.social.oauth.OAuthAccessManager;
import ar.com.zauber.commons.social.oauth.OAuthAccessToken;
import ar.com.zauber.commons.social.oauth.OAuthRequestToken;

/**
 * Implementación de {@link OAuthAccessManager} para Twitter4J
 * 
 * @author Francisco J. González Costanzó
 * @since Jan 22, 2010
 */
public class Twitter4JOAuthAccessManager implements OAuthAccessManager {

    private final TwitterFactory twitterFactory;

    private final Map<String, OAuthRequestToken> map = 
        new HashMap<String, OAuthRequestToken>();

    /** Creates the Twitter4JOAuthAccessManager. */
    public Twitter4JOAuthAccessManager(final TwitterFactory twitterFactory) {
        Validate.notNull(twitterFactory);
        this.twitterFactory = twitterFactory;
    }

    /** @return la authUrl para autenticar un usuario mediante OAuth */
    public final String getAuthUrl() {
        RequestToken requestToken = null;

        try {
            requestToken = twitterFactory.getInstance().getOAuthRequestToken();
        } catch (TwitterException e) {
            throw new OAuthAccessException(
                    "Exception when getting request token", e);
        }

        String authUrl = requestToken.getAuthorizationURL();

        map.put(requestToken.getToken(), new OAuthRequestTokenImpl(requestToken
                .getToken(), requestToken.getTokenSecret()));

        return authUrl;
    }

    /** @return el {@link OAuthAccessToken} para el oauthToken indicado */
    public final OAuthAccessToken getAccessToken(final String oauthToken) {
        Validate.notNull(oauthToken);
        OAuthRequestToken requestToken = map.get(oauthToken);

        AccessToken accessToken = null;

        try {
            accessToken = twitterFactory.getInstance().getOAuthAccessToken(
                    requestToken.getToken(), requestToken.getTokenSecret());
        } catch (TwitterException e) {
            throw new OAuthAccessException(
                    "Exception when getting access token for RequestToken: "
                            + requestToken, e);
        }

        return new Twitter4JOAuthAccessToken(accessToken);
    }

}

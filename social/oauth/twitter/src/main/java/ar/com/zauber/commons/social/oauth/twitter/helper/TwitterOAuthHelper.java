/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.social.oauth.twitter.helper;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;

import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;
import ar.com.zauber.commons.social.oauth.OAuthRequestToken;
import ar.com.zauber.commons.social.oauth.twitter.OAuthRequestTokenImpl;

/**
 * Clase de utilidad para el manejo de RequestTokens y AccessTokens
 * 
 * @author Francisco J. González Costanzó
 * @since Jan 22, 2010
 */
public class TwitterOAuthHelper {

    private final TwitterFactory twitterFactory;

    private final Map<String, OAuthRequestToken> map = 
        new HashMap<String, OAuthRequestToken>();

    /** Creates the TwitterOAuthHelper. */
    public TwitterOAuthHelper(final TwitterFactory twitterFactory) {
        Validate.notNull(twitterFactory);
        this.twitterFactory = twitterFactory;
    }

    /** @return la authUrl para autenticar un usuario mediante OAuth */
    public final String getAuthUrl() throws TwitterException {
        RequestToken requestToken = twitterFactory.getInstance()
                .getOAuthRequestToken();
        String authUrl = requestToken.getAuthorizationURL();

        map.put(requestToken.getToken(), new OAuthRequestTokenImpl(requestToken
                .getToken(), requestToken.getTokenSecret()));

        return authUrl;
    }

    /** @return el {@link AccessToken} para el oauthToken indicado */
    public final AccessToken getAccessToken(final String oauthToken)
            throws TwitterException {
        Validate.notNull(oauthToken);
        OAuthRequestToken requestToken = map.get(oauthToken);

        return twitterFactory.getInstance().getOAuthAccessToken(
                requestToken.getToken(), requestToken.getTokenSecret());
    }

}

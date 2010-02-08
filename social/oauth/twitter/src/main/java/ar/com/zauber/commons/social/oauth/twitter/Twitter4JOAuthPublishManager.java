/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.social.oauth.twitter;

import org.apache.commons.lang.Validate;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.http.AccessToken;
import ar.com.zauber.commons.social.oauth.OAuthAccessToken;
import ar.com.zauber.commons.social.oauth.OAuthPublishException;
import ar.com.zauber.commons.social.oauth.OAuthPublishManager;

/**
 * Implementación de {@link OAuthPublishManager} para Twitter4j
 * 
 * @author Francisco J. González Costanzó
 * @since Feb 8, 2010
 */
public class Twitter4JOAuthPublishManager implements OAuthPublishManager {

    private final TwitterFactory twitterFactory;

    /** Creates the Twitter4JOAuthPublishManager. */
    public Twitter4JOAuthPublishManager(final TwitterFactory twitterFactory) {
        Validate.notNull(twitterFactory);
        this.twitterFactory = twitterFactory;
    }

    /** @see OAuthPublishManager#publish(OAuthAccessToken, java.lang.String) */
    public final void publish(final OAuthAccessToken oAuthAccessToken,
            final String message) {
        AccessToken accessToken = new AccessToken(oAuthAccessToken.getToken(),
                oAuthAccessToken.getTokenSecret());
        Twitter twitter = twitterFactory.getInstance();
        twitter.setOAuthAccessToken(accessToken);
        try {
            twitter.updateStatus(message);
        } catch (TwitterException e) {
            throw new OAuthPublishException("Excepción al publicar en Twitter",
                    e);
        }
    }

    /** @see OAuthPublishManager#publish(String, String, String) */
    public final void publish(final String accessToken,
            final String accessTokenSecret, final String message) {
        this.publish(new Twitter4JOAuthAccessToken(new AccessToken(accessToken,
                accessTokenSecret)), message);
    }

}

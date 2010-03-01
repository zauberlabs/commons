/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.social.oauth.twitter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import twitter4j.TwitterException;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;
import ar.com.zauber.commons.social.oauth.OAuthAccessException;
import ar.com.zauber.commons.social.oauth.OAuthAccessManager;
import ar.com.zauber.commons.social.oauth.OAuthAccessToken;
import ar.com.zauber.commons.social.oauth.OAuthRequestToken;
import ar.com.zauber.commons.social.oauth.OAuthRequestTokenRepository;

/**
 * Implementación de {@link OAuthAccessManager} para Twitter4J
 * 
 * @author Francisco J. González Costanzó
 * @since Jan 22, 2010
 */
public class Twitter4JOAuthAccessManager implements OAuthAccessManager {

    private final TwitterFactory twitterFactory;

    private final OAuthRequestTokenRepository repository;

    /** Creates the Twitter4JOAuthAccessManager. */
    public Twitter4JOAuthAccessManager(final TwitterFactory twitterFactory,
            final OAuthRequestTokenRepository repository) {
        Validate.notNull(twitterFactory);
        Validate.notNull(repository);
        this.twitterFactory = twitterFactory;
        this.repository = repository;
    }

    /** @see OAuthAccessManager#getAuthUrl() */
    public final String getAuthUrl() {
        return getAuthUrl(null);
    }

    /** @see OAuthAccessManager#getAuthUrl(String) */
    public final String getAuthUrl(final String callbackUrl) {
        RequestToken requestToken = null;

        try {
            if (StringUtils.isNotEmpty(callbackUrl)) {
                requestToken = twitterFactory.getInstance()
                        .getOAuthRequestToken(callbackUrl);
            } else {
                requestToken = twitterFactory.getInstance()
                        .getOAuthRequestToken();
            }
        } catch (TwitterException e) {
            throw new OAuthAccessException(
                    "Exception when getting request token", e);
        }

        final String authUrl = requestToken.getAuthenticationURL();

        repository.save(requestToken.getToken(), new OAuthRequestTokenImpl(
                requestToken.getToken(), requestToken.getTokenSecret()));

        return authUrl;
    }

    /**
     * @param oauthToken
     *            el oauthToken
     * @param oauthVerifier
     *            el oauthVerifier en caso de haber uno, o <b>null</b>
     * @see OAuthAccessManager#getAccessToken(String, String)
     */
    public final OAuthAccessToken getAccessToken(final String oauthToken,
            final String oauthVerifier) {
        Validate.notNull(oauthToken);
        OAuthRequestToken requestToken = repository.get(oauthToken);

        AccessToken accessToken = null;

        try {
            if (oauthVerifier != null) {
                accessToken = twitterFactory.getInstance().getOAuthAccessToken(
                        requestToken.getToken(), requestToken.getTokenSecret(),
                        oauthVerifier);
            } else {
                accessToken = twitterFactory.getInstance().getOAuthAccessToken(
                        requestToken.getToken(), requestToken.getTokenSecret());
            }
        } catch (TwitterException e) {
            throw new OAuthAccessException(
                    "Exception when getting access token for RequestToken: "
                            + requestToken, e);
        }

        return new Twitter4JOAuthAccessToken(accessToken);
    }

    /** @see OAuthAccessManager#getAccessToken(String) */
    public final OAuthAccessToken getAccessToken(final String oauthToken) {
        return getAccessToken(oauthToken, null);
    }

    /** @see OAuthAccessManager#getAccessToken(HttpServletRequest) */
    public OAuthAccessToken getAccessToken(final HttpServletRequest request) {
        if (!request.getMethod().equals("GET")) {
            throw new OAuthAccessException(
                    "Authentication method not supported: "
                            + request.getMethod());
        }

        final String oauthToken = request.getParameter("oauth_token");
        final String oauthVerifier = request.getParameter("oauth_verifier");

        if (oauthToken != null) {
            return getAccessToken(oauthToken, oauthVerifier);
        }
        
        return null;
    }

}

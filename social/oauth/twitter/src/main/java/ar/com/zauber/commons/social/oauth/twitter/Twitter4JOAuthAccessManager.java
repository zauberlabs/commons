/**
 * Copyright (c) 2005-2011 Zauber S.A. <http://www.zaubersoftware.com/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ar.com.zauber.commons.social.oauth.twitter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;
import ar.com.zauber.commons.social.oauth.OAuthAccessException;
import ar.com.zauber.commons.social.oauth.OAuthAccessManager;
import ar.com.zauber.commons.social.oauth.OAuthAccessToken;
import ar.com.zauber.commons.social.oauth.OAuthConsumer;
import ar.com.zauber.commons.social.oauth.OAuthRequestToken;
import ar.com.zauber.commons.social.oauth.OAuthRequestTokenRepository;
import ar.com.zauber.commons.validate.Validate;

/**
 * {@link OAuthAccessManager} Implementation for Twitter4J.
 * 
 * @author Francisco J. González Costanzó
 * @since Jan 22, 2010
 */
public class Twitter4JOAuthAccessManager implements OAuthAccessManager {

    private final TwitterFactory twitterFactory;
    private final OAuthRequestTokenRepository repository;
    private final OAuthConsumer consumer;

    /** Creates the Twitter4JOAuthAccessManager. */
    public Twitter4JOAuthAccessManager(final TwitterFactory twitterFactory,
            final OAuthRequestTokenRepository repository,
            final OAuthConsumer consumer) {
        Validate.notNull(twitterFactory, repository, consumer);
        this.twitterFactory = twitterFactory;
        this.repository = repository;
        this.consumer = consumer;
    }

    /** @see OAuthAccessManager#getAuthenticationUrl() */
    public final String getAuthenticationUrl() {
        return getAuthenticationUrl(null);
    }

    /** @see OAuthAccessManager#getAuthenticationUrl(String) */
    public final String getAuthenticationUrl(final String callbackUrl) {
        final RequestToken requestToken = getRequestToken(callbackUrl);
        final String authUrl = requestToken.getAuthenticationURL();

        repository.save(requestToken.getToken(), new OAuthRequestTokenImpl(
                requestToken.getToken(), requestToken.getTokenSecret()));

        return authUrl;
    }

    /** @see OAuthAccessManager#getAuthorizationUrl() */
    public final String getAuthorizationUrl() throws OAuthAccessException {
        return getAuthorizationUrl(null);
    }

    /** @see OAuthAccessManager#getAuthorizationUrl(java.lang.String) */
    public final String getAuthorizationUrl(final String callbackUrl)
            throws OAuthAccessException {
        final RequestToken requestToken = getRequestToken(callbackUrl);
        final String authUrl = requestToken.getAuthorizationURL();

        repository.save(requestToken.getToken(), new OAuthRequestTokenImpl(
                requestToken.getToken(), requestToken.getTokenSecret()));

        return authUrl;
    }

    /** @return the request token */
    private RequestToken getRequestToken(final String callbackUrl) {
        try {
            Twitter twitter = twitterFactory.getOAuthAuthorizedInstance(
                    consumer.getKey(), consumer.getSecret());
            if (StringUtils.isNotEmpty(callbackUrl)) {
                return twitter.getOAuthRequestToken(callbackUrl);
            } else {
                return twitter.getOAuthRequestToken();
            }
        } catch (TwitterException e) {
            throw new OAuthAccessException(
                    "Exception when getting request token", e);
        }

    }

    /**
     * @param oauthToken
     *            the oauthToken
     * @param oauthVerifier
     *            the oauthVerifier if exists, or <b>null</b>
     * @see OAuthAccessManager#getAccessToken(String, String)
     */
    public final OAuthAccessToken getAccessToken(final String oauthToken,
            final String oauthVerifier) {
        Validate.notNull(oauthToken);
        OAuthRequestToken requestToken = repository.get(oauthToken);

        AccessToken accessToken = null;

        Twitter twitter = twitterFactory.getOAuthAuthorizedInstance(consumer
                .getKey(), consumer.getSecret());

        try {
            if (oauthVerifier == null) {
                accessToken = twitter.getOAuthAccessToken(requestToken
                        .getToken(), requestToken.getTokenSecret());
            } else {
                accessToken = twitter.getOAuthAccessToken(requestToken
                        .getToken(), requestToken.getTokenSecret(),
                        oauthVerifier);
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
    public final OAuthAccessToken getAccessToken(
            final HttpServletRequest request) {
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

/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
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
package ar.com.zauber.commons.social.oauth;

import javax.servlet.http.HttpServletRequest;

/**
 * OAuth login manager. Manages {@link OAuthRequestToken}'s and
 * {@link OAuthAccessToken}'s. When OAuth login is required the worlflow is the
 * following:
 * 
 * <p>
 * <ol>
 * <li>User asks for login (clicks oauth login button).</li>
 * <li>Implementation generates a {@link OAuthRequestToken}, stores it temporaly
 * for later use, and redirects the user to a new authentication or
 * authorization URL.</li>
 * <li>User logins at the OAuth provider site and is redirected back to a
 * <em>callback url</em>.</li>
 * <li>Implementation obtains the {@link OAuthAccessToken} from the OAuth
 * provider, using the oauth token sent with the user redirect.</li>
 * 
 * <p>
 * These methods are based on Twitter's implementation of OAuth. Check <a
 * href="http://dev.twitter.com/pages/auth#intro">Twitter Documentation</a> for
 * a detailed guide to OAuth.
 * 
 * @author Francisco J. González Costanzó
 * @since Feb 4, 2010
 */
public interface OAuthAccessManager {

    /**
     * Creates and returns a new URL where the user has to be redirected to
     * perform authentication. The user should be prompted for password only if
     * he has not authorized the app before. Otherwise, he should redirected
     * inmediately.
     * 
     * @return the URL
     */
    String getAuthenticationUrl() throws OAuthAccessException;

    /**
     * Creates and returns a new URL where the user has to be redirected to
     * perform authentication. After authenticating the user is redirected to
     * <em>callbackUrl</em> by the OAuth provider.
     * <p>
     * The user should be prompted for password only if he has not authorized
     * the app before. Otherwise, he should redirected inmediately.
     * 
     * @return the URL
     */
    String getAuthenticationUrl(String callbackUrl) throws OAuthAccessException;

    /**
     * Creates and returns a new URL where the user has to be redirected to
     * perform authorization. The user should prompted for password even tough
     * he has already authorized the application before.
     * 
     * @return the URL
     */
    String getAuthorizationUrl() throws OAuthAccessException;

    /**
     * Creates and returns a new URL where the user has to be redirected to
     * perform authorization. After authenticating the user is redirected to
     * <em>callbackUrl</em> by the OAuth provider.
     * <p>
     * The user should prompted for password even tough he has already
     * authorized the application before.
     * 
     * @return the URL
     */
    String getAuthorizationUrl(String callbackUrl) throws OAuthAccessException;

    /**
     * Returns the {@link OAuthAccessToken} asociated with a user that
     * previously requested an authorization or authentication URL.
     * 
     * @return the {@link OAuthAccessToken}
     */
    OAuthAccessToken getAccessToken(String oauthToken)
            throws OAuthAccessException;

    /**
     * Returns the {@link OAuthAccessToken} asociated with a user that
     * previously requested an authorization or authentication URL.
     * 
     * @return the {@link OAuthAccessToken}
     */
    OAuthAccessToken getAccessToken(String oauthToken, String oauthVerifier)
            throws OAuthAccessException;

    /**
     * Returns the {@link OAuthAccessToken} for the callback
     * {@link HttpServletRequest} made from the OAuth provider. The request must
     * be a GET and have the parameter <em>oauth_token</em> setted.
     * 
     * @return the {@link OAuthAccessToken}
     */
    OAuthAccessToken getAccessToken(HttpServletRequest request)
            throws OAuthAccessException;
}

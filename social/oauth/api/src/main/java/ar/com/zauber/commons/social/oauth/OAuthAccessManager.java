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
 *  <li>User asks for login (clicks oauth login button).</li>
 *  <li>Implementation redirects the user to a new authentication or authorization URL.</li>
 *  <li>User logins at the OAuth provider site and is redirected back.</li>
 *  <li>TODO:</li>
 * 
 * @author Francisco J. González Costanzó
 * @since Feb 4, 2010
 */
public interface OAuthAccessManager {

    /** @return la authUrl para autenticar un usuario mediante OAuth */
    String getAuthenticationUrl() throws OAuthAccessException;

    /**
     * @return la authUrl para autenticar un usuario mediante OAuth, con la url
     *         callback indicada
     */
    String getAuthenticationUrl(String callbackUrl) throws OAuthAccessException;

    /** @return la authUrl para autenticar un usuario mediante OAuth */
    String getAuthorizationUrl() throws OAuthAccessException;

    /**
     * @return la authUrl para autenticar un usuario mediante OAuth, con la url
     *         callback indicada
     */
    String getAuthorizationUrl(String callbackUrl) throws OAuthAccessException;

    /** @return el {@link OAuthAccessToken} para el oauthToken indicado */
    OAuthAccessToken getAccessToken(String oauthToken)
            throws OAuthAccessException;

    /**
     * @return el {@link OAuthAccessToken} para el oauthToken indicado,
     *         utilizando el oauthVerifier recibido en el callback
     */
    OAuthAccessToken getAccessToken(String oauthToken, String oauthVerifier)
            throws OAuthAccessException;

    /**
     * Este método obtiene el {@link OAuthAccessToken} del request hecho por
     * Twitter como callback. Este request debe ser de tipo GET y tener el
     * parámetro oauth_token.
     * 
     * @return el {@link OAuthAccessToken} para el request indicado
     */
    OAuthAccessToken getAccessToken(HttpServletRequest request)
            throws OAuthAccessException;
}

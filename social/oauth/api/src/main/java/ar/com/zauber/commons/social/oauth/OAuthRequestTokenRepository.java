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
package ar.com.zauber.commons.social.oauth;

/**
 * A temporal repository to store {@link OAuthRequestToken} while the user logs
 * in at the OAuth provider site, i.e. between authorization/authentication URL
 * generation and {@link OAuthAccessToken} generation.
 * 
 * @author Francisco J. González Costanzó
 * @since Feb 10, 2010
 */
public interface OAuthRequestTokenRepository {

    /**
     * Stores the {@link OAuthRequestToken} associated with specified
     * <em>oauthToken</em>
     */
    void save(String oauthToken, OAuthRequestToken requestToken);

    /**
     * @return the {@link OAuthRequestToken} associated with the specified
     *         <em>oauthToken</em>
     */
    OAuthRequestToken get(String oauthToken);

    /**
     * Removes the {@link OAuthRequestToken} associated with the specified
     * <em>oauthToken</em>
     */
    void remove(String oauthToken);
}

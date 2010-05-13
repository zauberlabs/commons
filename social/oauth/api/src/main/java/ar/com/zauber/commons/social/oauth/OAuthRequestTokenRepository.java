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

/**
 * Repositorio temporal para {@link OAuthRequestToken}
 * 
 * @author Francisco J. González Costanzó
 * @since Feb 10, 2010
 */
public interface OAuthRequestTokenRepository {

    /** Guarda el {@link OAuthRequestToken} bajo la clave <em>oauthToken</em> */
    void save(String oauthToken, OAuthRequestToken requestToken);

    /**
     * @return el {@link OAuthRequestToken} guardado bajo la clave
     *         <em>oauthToken</em>
     */
    OAuthRequestToken get(String oauthToken);

    /** Elimina el {@link OAuthRequestToken} bajo la clave <em>oauthToken</em> */
    void remove(String oauthToken);
}

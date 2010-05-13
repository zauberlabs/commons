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
 * Interface para publicación de contenido a través de un usuario autenticado
 * por OAuth.
 * 
 * @author Francisco J. González Costanzó
 * @since Feb 8, 2010
 */
public interface OAuthPublishManager {

    /**
     * Publica el mensaje en la cuenta del usuario correspondiente al access
     * token
     */
    void publish(OAuthAccessToken accessToken, String message);

    /**
     * Publica el mensaje en la cuenta del usuario correspondiente al access
     * token indicado
     */
    void publish(String accessToken, String accessTokenSecret, String message);

}

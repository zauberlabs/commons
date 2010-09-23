/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package ar.com.zauber.commons.social.oauth;

/**
 * Interface for content publication on behalf of an authenticated user.
 * 
 * @author Francisco J. González Costanzó
 * @since Feb 8, 2010
 */
public interface OAuthPublishManager {

    /**
     * Publish a text message on behalf of the user corresponding to the
     * specified {@link OAuthAccessToken}.
     */
    void publish(OAuthAccessToken accessToken, String message);

    /**
     * Publish a text message on behalf of the user corresponding to the
     * specified <b>access token</b> and <b>access token secret</b>.
     */
    void publish(String accessToken, String accessTokenSecret, String message);

}

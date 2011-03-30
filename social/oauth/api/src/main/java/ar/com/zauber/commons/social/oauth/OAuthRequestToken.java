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
 * An OAuth Request Token. This is generated when a user wants to login via
 * OAuth, at obtaining the authentication or authorization URL. It should be
 * stored temporaly, asociated with an oauth token, since it will be required
 * later for the {@link OAuthAccessToken} generation.
 * 
 * @author Francisco J. González Costanzó
 * @since Jan 15, 2010
 */
public interface OAuthRequestToken {

    /** @return the token */
    String getToken();

    /** @return the token secret */
    String getTokenSecret();

}

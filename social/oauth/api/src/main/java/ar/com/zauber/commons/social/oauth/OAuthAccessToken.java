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
 * An OAuth Access Token. It is composed by a <b>token</b> and a
 * <b>token secret</b>.
 * <p>
 * This is generated when a user grants permissions to the application and it
 * should be stored and used to act on behalf of the user after its generation.
 * 
 * @author Francisco J. González Costanzó
 * @since Jan 15, 2010
 */
public interface OAuthAccessToken {

    /** @return the token */
    String getToken();

    /** @return the token secret */
    String getTokenSecret();

    /** @return the screen name of the authenticated user */
    String getScreenName();

}

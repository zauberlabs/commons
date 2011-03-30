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

import java.io.Serializable;

import ar.com.zauber.commons.social.oauth.OAuthRequestToken;

/**
 * Simple implementation of {@link OAuthRequestToken}.
 * 
 * @author Francisco J. González Costanzó
 * @since Jan 15, 2010
 */
public class OAuthRequestTokenImpl implements OAuthRequestToken, Serializable {

    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1063009174951941880L;
    
    private final String token;
    private final String tokenSecret;

    /** Creates the OAuthAccessTokenImpl. */
    public OAuthRequestTokenImpl(final String token, final String tokenSecret) {
        this.token = token;
        this.tokenSecret = tokenSecret;
    }

    public final String getToken() {
        return token;
    }

    public final String getTokenSecret() {
        return tokenSecret;
    }

}

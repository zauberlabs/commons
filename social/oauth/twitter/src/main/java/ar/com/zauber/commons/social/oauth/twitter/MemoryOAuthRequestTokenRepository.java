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
package ar.com.zauber.commons.social.oauth.twitter;

import java.util.HashMap;
import java.util.Map;

import ar.com.zauber.commons.social.oauth.OAuthRequestToken;
import ar.com.zauber.commons.social.oauth.OAuthRequestTokenRepository;

/**
 * {@link OAuthRequestTokenRepository} that persists tokens in a {@link Map}.
 * 
 * @author Francisco J. González Costanzó
 * @since Feb 10, 2010
 */
public class MemoryOAuthRequestTokenRepository implements
        OAuthRequestTokenRepository {

    private final Map<String, OAuthRequestToken> map = 
        new HashMap<String, OAuthRequestToken>();

    /** @see OAuthRequestTokenRepository#get(java.lang.String) */
    public final OAuthRequestToken get(final String oauthToken) {
        return map.get(oauthToken);
    }

    /** @see OAuthRequestTokenRepository#remove(java.lang.String) */
    public final void remove(final String oauthToken) {
        map.remove(oauthToken);
    }

    /**
     * @see OAuthRequestTokenRepository#save(java.lang.String,
     *      OAuthRequestToken)
     */
    public final void save(final String oauthToken,
            final OAuthRequestToken requestToken) {
        map.put(oauthToken, requestToken);
    }

}

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

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.social.oauth.OAuthConsumer;

/**
 * Simple Implementation of {@link OAuthConsumer}
 * 
 * @author Francisco J. González Costanzó
 * @since Jan 15, 2010
 */
public class OAuthConsumerImpl implements OAuthConsumer {

    private final String consumerKey;
    private final String consumerSecret;

    /**
     * Creates the OAuthConsumerImpl.
     * 
     */
    public OAuthConsumerImpl(final String consumerKey,
            final String consumerSecret) {
        Validate.notNull(consumerKey);
        Validate.notNull(consumerSecret);
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
    }

    /** @see OAuthConsumer#getKey() */
    public final String getKey() {
        return consumerKey;
    }

    /** @see OAuthConsumer#getSecret() */
    public final String getSecret() {
        return consumerSecret;
    }

}

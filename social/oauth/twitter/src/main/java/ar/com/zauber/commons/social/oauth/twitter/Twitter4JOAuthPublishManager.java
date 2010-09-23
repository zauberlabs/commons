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
package ar.com.zauber.commons.social.oauth.twitter;

import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;
import ar.com.zauber.commons.social.oauth.OAuthAccessToken;
import ar.com.zauber.commons.social.oauth.OAuthConsumer;
import ar.com.zauber.commons.social.oauth.OAuthPublishException;
import ar.com.zauber.commons.social.oauth.OAuthPublishManager;
import ar.com.zauber.commons.validate.Validate;

/**
 * {@link OAuthPublishManager} Twitter4J implementation
 * 
 * @author Francisco J. González Costanzó
 * @since Feb 8, 2010
 */
public class Twitter4JOAuthPublishManager implements OAuthPublishManager {

    private final TwitterFactory twitterFactory;
    private final OAuthConsumer consumer;

    /** Creates the Twitter4JOAuthPublishManager. */
    public Twitter4JOAuthPublishManager(final TwitterFactory twitterFactory,
            final OAuthConsumer consumer) {
        Validate.notNull(twitterFactory, consumer);
        this.twitterFactory = twitterFactory;
        this.consumer = consumer;
    }

    /** @see OAuthPublishManager#publish(OAuthAccessToken, java.lang.String) */
    public final void publish(final OAuthAccessToken oAuthAccessToken,
            final String message) {
        AccessToken accessToken = new AccessToken(oAuthAccessToken.getToken(),
                oAuthAccessToken.getTokenSecret());
        try {
            twitterFactory.getOAuthAuthorizedInstance(consumer.getKey(),
                    consumer.getSecret(), accessToken).updateStatus(message);
        } catch (TwitterException e) {
            throw new OAuthPublishException(
                    "Exception at updating twitter status", e);
        }
    }

    /** @see OAuthPublishManager#publish(String, String, String) */
    public final void publish(final String accessToken,
            final String accessTokenSecret, final String message) {
        this.publish(new Twitter4JOAuthAccessToken(new AccessToken(accessToken,
                accessTokenSecret)), message);
    }

}

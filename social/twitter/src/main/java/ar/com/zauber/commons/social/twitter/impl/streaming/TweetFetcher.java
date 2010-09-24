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
package ar.com.zauber.commons.social.twitter.impl.streaming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.commons.dao.Transformer;
import ar.com.zauber.commons.social.twitter.api.Tweet;
import ar.com.zauber.commons.social.twitter.api.streaming.filter.BoundingBox;
import ar.com.zauber.commons.social.twitter.api.streaming.filter.StreamingFilter;
import ar.com.zauber.commons.validate.Validate;

/**
 * Models a connection to the Twitter Streaming API.
 * 
 * 
 * @author Francisco J. Gonz�lez Costanz�
 * @since Sep 20, 2010
 */
public class TweetFetcher {

    private final Logger logger = LoggerFactory
            .getLogger(TweetFetcher.class);

    private final TwitterStream stream;
    private final StreamingFilter filter;
    private final Transformer<Status, Tweet> transformer;
    private final Closure<Tweet> closure;

    private boolean streamStarted = false;

    /** Creates the TweetFetcher. */
    public TweetFetcher(final String user, final String password,
            final StreamingFilter filter,
            final Transformer<Status, Tweet> transformer,
            final Closure<Tweet> closure) {
        Validate.notBlank(user, password);
        Validate.notNull(filter, transformer, closure);
        this.stream = createStream(user, password);
        this.filter = filter;
        this.closure = closure;
        this.transformer = transformer;
    }

    /**
     * Crea el {@link TwitterStream}
     * 
     * @param user
     * @param password
     * @return
     */
    private TwitterStream createStream(final String user, final String password) {
        return new TwitterStreamFactory(new StatusListener() {

            @Override
            public void onStatus(final Status status) {
                try {
                    Tweet t = transformer.transform(status);
                    closure.execute(t);
                } catch (Throwable ex) {
                    logger.error("Exception en onStatus", ex);
                }

            }

            @Override
            public void onException(final Exception e) {
                logger.error("Exception on TwitterStream", e);
            }

            @Override
            public void onTrackLimitationNotice(
                    final int numberOfLimitedStatuses) {
                logger.warn("onTrackLimitationNotice: Number of limited "
                        + "statuses: {}", numberOfLimitedStatuses);
            }

            @Override
            public void onDeletionNotice(
                    final StatusDeletionNotice statusDeletionNotice) {
                logger.warn("statusDeletionNotice: {}", statusDeletionNotice);
            }
        }).getInstance(user, password);
    }

    /**
     * Abre el Twitter Stream
     */
    public final void openStream() {
        if (!streamStarted) {
            logger.info("Opening Twitter Stream!");
            try {
                stream.filter(toFilterQuery(filter));
                streamStarted = true;
            } catch (TwitterException e) {
                logger.error("Exception al abrir stream", e);
            }
        } else {
            logger.warn("Stream is already opened!");
        }
    }

    /**
     * Transforms a {@link TwitterFilter} to a {@link FilterQuery}
     * 
     * @param filter2
     *            the {@link TwitterFilter}
     * @return the {@link FilterQuery}
     */
    private FilterQuery toFilterQuery(final StreamingFilter filter2) {
        final int count = filter2.getPreviousStatuses();
        final int[] follow = filter2.getUserIds();
        final String[] track = filter2.getKeywords();

        double[][] locations;
        final BoundingBox[] boxes = filter2.getBoxes();
        if (boxes != null) {
            locations = new double[boxes.length * 2][2];
            for (int i = 0; i < boxes.length; i += 2) {
                BoundingBox box = boxes[i];
                locations[i][0] = box.getSwLong();
                locations[i][1] = box.getSwLat();
                locations[i + 1][0] = box.getNeLat();
                locations[i + 1][1] = box.getNeLat();
            }
        } else {
            locations = new double[][] {};
        }

        return new FilterQuery(count, follow, track, locations);
    }

    /**
     * Cierra el stream.
     */
    public final void closeStream() {
        if (streamStarted) {
            logger.info("Closing Twitter Stream.");
            stream.cleanup();
            streamStarted = false;
        } else {
            logger.warn("Stream is already closed!");
        }
    }

}

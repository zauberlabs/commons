/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.social.oauth.twitter;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.social.oauth.OAuthConsumer;

/**
 * Implementación simple de {@link OAuthConsumer}
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

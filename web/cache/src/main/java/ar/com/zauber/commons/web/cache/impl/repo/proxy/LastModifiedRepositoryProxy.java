/**
 *  Copyright (c) 2010 Terra.com  -- All rights reserved
 */
package ar.com.zauber.commons.web.cache.impl.repo.proxy;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.zauber.commons.web.cache.api.repo.EntityKey;
import ar.com.zauber.commons.web.cache.api.repo.LastModifiedRepository;
import ar.com.zauber.commons.web.cache.api.repo.StringEntityKey;

/**
 * {@link LastModifiedRepository} proxy, it propagagets through a publisher
 * the invalidation messages.
 * 
 * @author Mariano Cortesi
 * @since Apr 15, 2010
 */
public class LastModifiedRepositoryProxy implements
        LastModifiedRepository<StringEntityKey> {

    private static final Logger LOGGER = 
        LoggerFactory.getLogger(LastModifiedRepositoryProxy.class);
    private Publisher<LastModifiedMessage> publisher;
    
    
    /** Creates the LastModifiedCacheJmsProxy.  */
    public LastModifiedRepositoryProxy(
            final Publisher<LastModifiedMessage> publisher) {
        this.publisher = publisher;
    }

    /** @see LastModifiedRepository#clearTimestamp(EntityKey) */
    public final void clearTimestamp(final StringEntityKey key) {
        throw new UnsupportedOperationException("don't need by now");
    }

    /** @see LastModifiedRepository#getMaxTimestamp(K[]) */
    public final Long getMaxTimestamp(final StringEntityKey... keys) {
        throw new UnsupportedOperationException("don't need by now");
    }

    /** @see LastModifiedRepository#getTimestamp(EntityKey) */
    public final Long getTimestamp(final StringEntityKey key) {
        throw new UnsupportedOperationException("don't need by now");
    }

    /** @see LastModifiedRepository#updateTimestamp(EntityKey, Date) */
    public final void updateTimestamp(final StringEntityKey key, 
            final Date timestamp) {
        String keyString = key.getAsString();
        LOGGER.debug("sending invalidation: ({},{})", keyString, timestamp);
        this.publisher.publish(
                new LastModifiedMessage(timestamp, key.getAsString()));
    }

    /** @see LastModifiedRepository#updateTimestamp(EntityKey, Long) */
    public final void updateTimestamp(final StringEntityKey key, 
            final Long timestamp) {
        this.updateTimestamp(key, new Date(timestamp));
    }

}

/**
 *  Copyright (c) 2010 Terra.com  -- All rights reserved
 */
package ar.com.zauber.commons.web.cache.impl.repo.proxy;

import ar.com.zauber.commons.web.cache.api.repo.LastModifiedRepository;
import ar.com.zauber.commons.web.cache.api.repo.StringEntityKey;
import ar.com.zauber.commons.web.cache.impl.repo.SimpleEntityKey;

/**
 * <p>
 * Process all the {@link LastModifiedRepositoryProxy} events, and pass them
 * to a {@link LastModifiedRepository} implementation.
 *  
 * <p>
 * Supports only {@link StringEntityKey}.
 * 
 * @author Mariano Cortesi
 * @since Apr 15, 2010
 */
public class LastModifiedRepositoryProxyProcessor 
    implements Processor<LastModifiedMessage> {

    private LastModifiedRepository<StringEntityKey> lastModifiedRepository;
    
    /**
     * Creates the LastModifiedCacheProxyProcessor.
     */
    public LastModifiedRepositoryProxyProcessor(
            final LastModifiedRepository<StringEntityKey> lastModifiedRepository) {
        this.lastModifiedRepository = lastModifiedRepository;
    }


    /** @see Processor#process(Object) */
    public final void process(final LastModifiedMessage entry) {
        lastModifiedRepository.updateTimestamp(
                new SimpleEntityKey(entry.getEntityKey()), 
                entry.getTimestamp());
    }

}

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
package ar.com.zauber.commons.web.cache.impl.repo.ehcache;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.zauber.commons.web.cache.api.repo.EntityKey;
import ar.com.zauber.commons.web.cache.api.repo.LastModifiedRepository;
import ar.com.zauber.commons.web.cache.api.repo.StringEntityKey;

/**
 * {@link LastModifiedRepository} implementation with persistense support
 * through EhCache.
 * 
 * @author Mariano A. Cortesi
 * @author Francisco J. González Costanzó
 * @since Mar 22, 2010
 */
public class EhCacheLastModifiedRepository implements
        LastModifiedRepository<StringEntityKey> {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(EhCacheLastModifiedRepository.class);

    private final Cache cache;

    /**
     * Creates the LastModifiedRepository.
     */
    public EhCacheLastModifiedRepository(final Cache ehCache) {
        Validate.notNull(ehCache);
        this.cache = ehCache;
    }

    /** @see LastModifiedRepository#clearTimestamp(EntityKey) */
    public final void clearTimestamp(final StringEntityKey key) {
        String keyString = key.getAsString();
        LOGGER.debug("httpcache cleared: ({})", keyString);
        cache.remove(keyString);
    }

    /** @see LastModifiedRepository#getMaxTimestamp(EntityKey[]) */
    public final Long getMaxTimestamp(final StringEntityKey... keys) {
        return getMaxTimestamp(Arrays.asList(keys));
    }

    /** @see LastModifiedRepository#getMaxTimestamp(List) */
    public final Long getMaxTimestamp(final List<StringEntityKey> keys) {
        Long maxTimestamp = null;
        
        for (StringEntityKey key : keys) {
            Long ts = getTimestamp(key);
            if (maxTimestamp == null) {
                maxTimestamp = ts;
            } else if (ts != null && maxTimestamp.compareTo(ts) < 0) {
                maxTimestamp = ts;
            }
        }
        return maxTimestamp;
    }
    
    /** @see LastModifiedRepository#getTimestamp(EntityKey) */
    public final Long getTimestamp(final StringEntityKey key) {
        Element element = cache.get(key.getAsString());
        
        if (element != null) {
            return (Long) element.getValue();
        } else {
            return null;
        }        
    }

    /** @see LastModifiedRepository#updateTimestamp(EntityKey, Date) */
    public final void updateTimestamp(final StringEntityKey key, 
            final Date timestamp) {
        updateTimestamp(key, timestamp.getTime());
    }

    /** @see LastModifiedRepository#updateTimestamp(EntityKey, Long) */
    public final void updateTimestamp(final StringEntityKey key, 
            final Long timestamp) {
        String keyString = key.getAsString();
        
        LOGGER.debug("httpcache invalidated: ({},{})", keyString , timestamp);
        cache.put(new Element(keyString, timestamp));        
    }


}

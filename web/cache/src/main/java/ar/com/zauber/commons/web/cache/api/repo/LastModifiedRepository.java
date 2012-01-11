/**
 * Copyright (c) 2005-2012 Zauber S.A. <http://www.zaubersoftware.com/>
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
package ar.com.zauber.commons.web.cache.api.repo;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * Models a Repository of Last-Modified information about entities.
 * 
 * <p>
 * It can be used to support conditions HTTP requests within a web application.
 * 
 * @param <K> Type of {@link EntityKey}
 * 
 * @author Mariano Cortesi
 * @since Apr 15, 2010
 */
public interface LastModifiedRepository<K extends EntityKey> {

    /**
     * Updates the timestamp for an Entity identified by a key.
     * 
     * @param key
     *            Entity key
     * @param timestamp
     *            new Timestamp for the Entity
     */
    void updateTimestamp(K key, Date timestamp);

    /** @see {@link #updateTimestamp(EntityKey, Date)} */
    void updateTimestamp(K key, Long timestamp);

    /**
     * Clear the timestamp for an Entity identified by a key.
     * 
     * @param key
     *            Entity key
     */
    void clearTimestamp(K key);

    /**
     * Get the timestamp (milliseconds) for an entity.
     * 
     * @param key
     *            Entity key
     * @return timestamp
     */
    Long getTimestamp(K key);

    /**
     * Get the max (oldest) timestamp for a collection of entities.
     * 
     * @param keys
     *            Collection of entities key.
     * @return oldest timestamp
     */
    Long getMaxTimestamp(K... keys);

    /**
     * Get the max (oldest) timestamp for a collection of entities.
     * 
     * @param keys
     *            Collection of entities key.
     * @return oldest timestamp
     */
    Long getMaxTimestamp(List<K> keys);

}
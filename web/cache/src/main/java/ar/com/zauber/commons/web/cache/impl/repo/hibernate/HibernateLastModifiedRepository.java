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
package ar.com.zauber.commons.web.cache.impl.repo.hibernate;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import ar.com.zauber.commons.web.cache.api.repo.EntityKey;
import ar.com.zauber.commons.web.cache.api.repo.LastModifiedRepository;
import ar.com.zauber.commons.web.cache.api.repo.StringEntityKey;

/**
 * <p>
 * {@link LastModifiedRepository} implementation that uses Hibernate to persist
 * last-modified records.
 * 
 * <p>
 * Supports only {@link StringEntityKey}
 * 
 * @author Mariano Cortesi
 * @since May 12, 2010
 */
public class HibernateLastModifiedRepository implements
        LastModifiedRepository<StringEntityKey> {

    private final SessionFactory sessionFactory;

    /**
     * Creates the HibernateLastModifiedRepository.
     * 
     * @param sessionFactory
     *            Hibernate {@link SessionFactory}
     */
    public HibernateLastModifiedRepository(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /** @see LastModifiedRepository#clearTimestamp(EntityKey) */
    @Transactional
    public final void clearTimestamp(final StringEntityKey key) {
        Validate.notNull(key);
        Query query = this.sessionFactory.getCurrentSession().getNamedQuery(
                "lastModified.deleteForEntity");

        query.setString("entityKey", key.getAsString());
        query.executeUpdate();
    }

    /** @see LastModifiedRepository#getMaxTimestamp(K[]) */
    @Transactional(readOnly = true)
    public final Long getMaxTimestamp(final StringEntityKey... keys) {
        // optimización para el caso simple
        if (keys.length == 1) {
            return this.getTimestamp(keys[0]);
        } else {
            return this.getMaxTimestamp(Arrays.asList(keys));
        }
    }

    /** @see LastModifiedRepository#getMaxTimestamp(List) */
    public final Long getMaxTimestamp(final List<StringEntityKey> keys) {
        if (keys.size() == 0) {
            return this.getTimestamp(keys.get(0));
        }
        
        String[] keyStrings = new String[keys.size()];
        for (int i = 0; i < keys.size(); i++) {
            keyStrings[i] = keys.get(i).getAsString();
        }
        
        Query query = this.sessionFactory.getCurrentSession().getNamedQuery(
                "lastModified.orderedTimestampsForEntities");

        query.setParameterList("entitiesKey", keyStrings);
        query.setMaxResults(1);
        return (Long) query.uniqueResult();
    }
    
    /** @see LastModifiedRepository#getTimestamp(EntityKey) */
    @Transactional(readOnly = true)
    public final Long getTimestamp(final StringEntityKey key) {
        Validate.notNull(key);
        Query query = this.sessionFactory.getCurrentSession().getNamedQuery(
                "lastModified.timestampForEntity");

        query.setString("entityKey", key.getAsString());
        return (Long) query.uniqueResult();
    }

    /** @see LastModifiedRepository#updateTimestamp(EntityKey, Date) */
    @Transactional
    public final void updateTimestamp(final StringEntityKey key,
            final Date timestamp) {
        Validate.notNull(key);
        Validate.notNull(timestamp);

        Query query = this.sessionFactory.getCurrentSession().getNamedQuery(
                "lastModified.updateForEntity");

        query.setString("entityKey", key.getAsString());
        query.setTimestamp("timestamp", timestamp);
        int updatedEntities = query.executeUpdate();

        // create record if no one exist
        if (updatedEntities == 0) {
            this.sessionFactory.getCurrentSession().save(
                    new LastModifiedRecord(timestamp, key.getAsString()));
        }
    }

    /** @see LastModifiedRepository#updateTimestamp(EntityKey, Long) */
    @Transactional
    public final void updateTimestamp(final StringEntityKey key,
            final Long timestamp) {
        Validate.notNull(timestamp);
        this.updateTimestamp(key, new Date(timestamp));
    }


}

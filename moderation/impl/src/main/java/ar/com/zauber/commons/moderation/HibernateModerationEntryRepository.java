/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
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
package ar.com.zauber.commons.moderation;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.auth.AuthenticationUserMapper;
import ar.com.zauber.commons.dao.Order;
import ar.com.zauber.commons.dao.Ordering;
import ar.com.zauber.commons.date.DateProvider;
import ar.com.zauber.commons.repository.Reference;
import ar.com.zauber.commons.repository.Repository;
import ar.com.zauber.commons.repository.query.SimpleQuery;
import ar.com.zauber.commons.repository.query.connectors.AndConnector;
import ar.com.zauber.commons.repository.query.filters.BaseFilter;
import ar.com.zauber.commons.repository.query.filters.CompositeFilter;
import ar.com.zauber.commons.repository.query.filters.EqualsPropertyFilter;
import ar.com.zauber.commons.repository.query.filters.Filter;
import ar.com.zauber.commons.repository.query.values.SimpleValue;

/**
 * Implementación de {@link ModerationEntryRepository} que persiste las entradas
 * mediante un {@link Repository}
 * 
 * @author Pablo Grigolatto
 * @since Oct 7, 2009
 */
public class HibernateModerationEntryRepository 
    implements ModerationEntryRepository {

    private final DateProvider dateProvider;
    private final AuthenticationUserMapper<String> authUserMapper;
    private final Repository repository;
    private final ModerationEntryFactory moderationEntryFactory;
    
    /** Constructor */
    public HibernateModerationEntryRepository(final DateProvider dateProvider, 
            final AuthenticationUserMapper<String> authUserMapper, 
            final Repository repository, 
            final ModerationEntryFactory moderationEntryFactory) {
        
        Validate.notNull(dateProvider);
        Validate.notNull(authUserMapper);
        Validate.notNull(repository);
        Validate.notNull(moderationEntryFactory);
        
        this.dateProvider = dateProvider;
        this.authUserMapper = authUserMapper;
        this.repository = repository;
        this.moderationEntryFactory = moderationEntryFactory;
    }

    
    /** @see ModerationEntryRepository#getModerationEntries(Reference) */
    public final List<ModerationEntry> getModerationEntries(
            final Reference<Moderateable> reference) {
        final Filter comp = new CompositeFilter(new AndConnector(),
                Arrays.asList(
                    new BaseFilter[] {
                        new EqualsPropertyFilter("reference.id", 
                                new SimpleValue(Long.valueOf(
                                        reference.getId()))),
                        new EqualsPropertyFilter("reference.clazzName",
                            new SimpleValue(reference.getClazz().getName()))
                    })
                );
        
        return (List)repository.find(
            new SimpleQuery<ModerationEntry>(
                moderationEntryFactory.getClazz(),
                comp, 
                null, 
                new Ordering(new Order("moderatedAt")))
        );
    }

    /** @see ModerationEntryRepository#getModerationEntries(Moderateable) */
    public final List<ModerationEntry> getModerationEntries(final Moderateable m) {
        return getModerationEntries(
                (Reference<Moderateable>) m.generateReference());
    }

    /** @see ModerationEntryRepository#notifyChange(
     *       Reference, ModerationState, ModerationState) */
    public final void notifyChange(final Reference<Moderateable> reference,
            final ModerationState oldState, final ModerationState newState) {
        repository.saveOrUpdate(
            moderationEntryFactory.create(
                    dateProvider.getDate(),
                    authUserMapper.getUser(), 
                    oldState, 
                    newState, 
                    reference));
    }

    /** @see ModerationEntryRepository#notifyChange(
     *       Moderateable, ModerationState, ModerationState) */
    public final void notifyChange(final Moderateable moderateable,
            final ModerationState oldState, final ModerationState newState) {
        
        notifyChange((Reference<Moderateable>) moderateable.generateReference(), 
                oldState, newState);
    }
}

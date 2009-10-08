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
package ar.com.zauber.commons.moderation.model;

import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.auth.AuthenticationUserMapper;
import ar.com.zauber.commons.dao.Order;
import ar.com.zauber.commons.dao.Ordering;
import ar.com.zauber.commons.date.DateProvider;
import ar.com.zauber.commons.moderation.Moderateable;
import ar.com.zauber.commons.moderation.ModerationEntry;
import ar.com.zauber.commons.moderation.ModerationEntryRepository;
import ar.com.zauber.commons.moderation.ModerationState;
import ar.com.zauber.commons.repository.Reference;
import ar.com.zauber.commons.repository.Repository;
import ar.com.zauber.commons.repository.query.SimpleQuery;
import ar.com.zauber.commons.repository.query.filters.EqualsPropertyFilter;
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

    /** Constructor */
    public HibernateModerationEntryRepository(final DateProvider dateProvider, 
            final AuthenticationUserMapper<String> authUserMapper, 
            final Repository repository) {
        
        Validate.notNull(dateProvider);
        Validate.notNull(authUserMapper);
        Validate.notNull(repository);
        
        this.dateProvider = dateProvider;
        this.authUserMapper = authUserMapper;
        this.repository = repository;
    }

    
    /** @see ModerationEntryRepository#getModerationEntries(Reference) */
    public final List<ModerationEntry> getModerationEntries(
            final Reference<Moderateable> reference) {
        
        throw new NotImplementedException();
    }

    /** @see ModerationEntryRepository#getModerationEntries(Moderateable) */
    public final List<ModerationEntry> getModerationEntries(final Moderateable m) {
        return repository.find(
            new SimpleQuery<ModerationEntry>(
                ModerationEntry.class,
                new EqualsPropertyFilter("reference", 
                        new SimpleValue(m.generateReference())), 
                null, 
                new Ordering(new Order("moderatedAt")))
        );
    }

    /** @see ModerationEntryRepository#notifyChange(
     *       Reference, ModerationState, ModerationState) */
    public final void notifyChange(final Reference<Moderateable> reference,
            final ModerationState oldState, final ModerationState newState) {
        
        throw new NotImplementedException();
    }

    /** @see ModerationEntryRepository#notifyChange(
     *       Moderateable, ModerationState, ModerationState) */
    public final void notifyChange(final Moderateable moderateable,
            final ModerationState oldState, final ModerationState newState) {
        
        final ModerationEntry entry = new HibernateInmutableModerationEntry(
                (Reference<Moderateable>) moderateable.generateReference(), 
                oldState, newState, 
                dateProvider.getDate(), authUserMapper.getUser());
        
        repository.saveOrUpdate(entry);
    }
    
}

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.auth.AuthenticationUserMapper;
import ar.com.zauber.commons.date.DateProvider;
import ar.com.zauber.commons.moderation.InmutableModerationEntry;
import ar.com.zauber.commons.moderation.Moderateable;
import ar.com.zauber.commons.moderation.ModerationEntry;
import ar.com.zauber.commons.moderation.ModerationEntryRepository;
import ar.com.zauber.commons.moderation.ModerationState;
import ar.com.zauber.commons.repository.Reference;

/**
 * Implementacion para test
 * 
 * @author Pablo Grigolatto
 * @since Oct 6, 2009
 */
public class MockModerationEntryRepository implements ModerationEntryRepository {

    private final Map<Moderateable, List<ModerationEntry>> map;
    private final DateProvider dateProvider;
    private final AuthenticationUserMapper<String> authUserMapper;

    /** Constructor */
    public MockModerationEntryRepository(final DateProvider dateProvider, 
            final AuthenticationUserMapper<String> authUserMapper) {
        
        Validate.notNull(dateProvider);
        Validate.notNull(authUserMapper);
        
        this.dateProvider = dateProvider;
        this.authUserMapper = authUserMapper;
        this.map = new HashMap<Moderateable, List<ModerationEntry>>();
    }
    
    /** @see ModerationEntryRepository#getModerationEntries(Reference) */
    public final List<ModerationEntry> getModerationEntries(
            final Reference<Moderateable> reference) {
        throw new NotImplementedException();
    }

    /** @see ModerationEntryRepository#notifyChange(
     *       Reference, ModerationState, ModerationState) */
    public final void notifyChange(final Reference<Moderateable> reference,
            final ModerationState oldState, final ModerationState newState) {
        throw new NotImplementedException();
    }

    /** @see ModerationEntryRepository#getModerationEntries(Moderateable) */
    public final List<ModerationEntry> getModerationEntries(final Moderateable m) {
        if(map.containsKey(m)) {
            return Collections.unmodifiableList(map.get(m));
        }
        return Collections.unmodifiableList(new ArrayList<ModerationEntry>());
    }

    /** @see ModerationEntryRepository#notifyChange(
     *       Moderateable, ModerationState, ModerationState) */
    public final void notifyChange(final Moderateable moderateable,
            final ModerationState oldState, final ModerationState newState) {
        
        if(!map.containsKey(moderateable)) {
            map.put(moderateable, new ArrayList<ModerationEntry>());
        }
        map.get(moderateable).add(
            new InmutableModerationEntry(
                (Reference<Moderateable>) moderateable.generateReference(), 
                oldState, newState, 
                dateProvider.getDate(), authUserMapper.getUser()));
    }
    
}

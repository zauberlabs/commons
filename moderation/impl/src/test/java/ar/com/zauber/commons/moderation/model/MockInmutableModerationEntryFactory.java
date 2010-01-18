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
package ar.com.zauber.commons.moderation.model;

import java.util.Date;

import ar.com.zauber.commons.moderation.Moderateable;
import ar.com.zauber.commons.moderation.ModerationEntry;
import ar.com.zauber.commons.moderation.ModerationEntryFactory;
import ar.com.zauber.commons.moderation.ModerationState;
import ar.com.zauber.commons.repository.Reference;

/**
 * Implementación de {@link ModerationEntryFactory} 
 * que crea {@link MockInmutableModerationEntry}
 *
 * @author Pablo Grigolatto
 * @since Oct 9, 2009
 */
public class MockInmutableModerationEntryFactory implements ModerationEntryFactory {

    /** @see ModerationEntryFactory#create(
     *       Date, String, ModerationState, ModerationState, Reference) */
    public final ModerationEntry create(final Date moderatedAt, 
            final String moderatedBy,
            final ModerationState initialState, 
            final ModerationState finalState,
            final Reference<Moderateable> entityReference) {
        
        return new MockInmutableModerationEntry(
                entityReference, 
                initialState, 
                finalState, 
                moderatedAt, 
                moderatedBy);        
    }

    /** @see ModerationEntryFactory#getClazz() */
    public final Class getClazz() {
        return MockInmutableModerationEntry.class;
    }

}

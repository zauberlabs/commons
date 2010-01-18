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

import javax.persistence.Entity;

import org.hibernate.annotations.TypeDef;

import ar.com.zauber.commons.moderation.InmutableModerationEntry;
import ar.com.zauber.commons.moderation.Moderateable;
import ar.com.zauber.commons.moderation.ModerationState;
import ar.com.zauber.commons.repository.Reference;

/**
 * Implementación de {@link InmutableModerationEntry} de prueba que posee 
 * la información necesaria para persistir los estados mediante hibernate 
 * 
 * @author Pablo Grigolatto
 * @since Oct 7, 2009
 */
@Entity
@TypeDef(name = "type_moderationState", typeClass = EnumModerationState.class)
public class MockInmutableModerationEntry extends
        InmutableModerationEntry {
    
    /** Constructor */
    private MockInmutableModerationEntry() {
        // default
    }
    
    /** Constructor */
    public MockInmutableModerationEntry(final Reference<Moderateable> reference,
            final ModerationState initialState, final ModerationState finalState,
            final Date moderatedAt, final String moderatedBy) {
        super(reference, initialState, finalState, moderatedAt, moderatedBy);
    }
    
}

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

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.repository.Reference;

/**
 * Implementación inmutable de {@link ModerationEntry}
 * 
 * @author Pablo Grigolatto
 * @since Oct 6, 2009
 */
public class InmutableModerationEntry implements ModerationEntry {

    private final Reference<Moderateable> reference;
    private final ModerationState initialState;
    private final ModerationState finalState;
    private final Date moderatedAt;
    private final String moderatedBy;

    /** Constructor */
    public InmutableModerationEntry(final Reference<Moderateable> reference, 
            final ModerationState initialState, final ModerationState finalState, 
            final Date moderatedAt, final String moderatedBy) {
        
        Validate.notNull(reference);
        Validate.notNull(initialState);
        Validate.notNull(finalState);
        Validate.notNull(moderatedAt);
        Validate.isTrue(StringUtils.isNotBlank(moderatedBy));
        
        this.reference = reference;
        this.initialState = initialState;
        this.finalState = finalState;
        this.moderatedAt = moderatedAt;
        this.moderatedBy = moderatedBy;
    }
    
    /** @see ModerationEntry#getEntityReference() */
    public final Reference<Moderateable> getEntityReference() {
        return reference;
    }
    
    /** @see ModerationEntry#getInitialState() */
    public final ModerationState getInitialState() {
        return initialState;
    }

    /** @see ModerationEntry#getFinalState() */
    public final ModerationState getFinalState() {
        return finalState;
    }

    /** @see ModerationEntry#getModeratedAt() */
    public final Date getModeratedAt() {
        return new Date(moderatedAt.getTime());
    }

    /** @see ModerationEntry#getModeratedBy() */
    public final String getModeratedBy() {
        return moderatedBy;
    }
    
}

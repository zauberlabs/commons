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
package ar.com.zauber.commons.moderation;

import java.util.List;

import javax.persistence.MappedSuperclass;

/**
 * Entidades moderables que obtienen su información de moderación 
 * de un {@link BaseRepositoryModerateableEntity}
 * 
 * @author Pablo Grigolatto
 * @since Oct 6, 2009
 */
@MappedSuperclass
public abstract class BaseRepositoryModerateableEntity 
    extends BaseModerateableEntity {

    /** Constructor */
    protected BaseRepositoryModerateableEntity() {
        // default
    }
    
    /** Constructor */
    public BaseRepositoryModerateableEntity(final ModerationState state) {
        super(state);
    }
    
    /** @return el repositorio de entradas */
    protected abstract ModerationEntryRepository getModerationEntryRepository();
    
    /** @see Moderateable#getModerationHistory() */
    public final List<ModerationEntry> getModerationHistory() {
        return getModerationEntryRepository().getModerationEntries(this);
    }
    
    /** @see BaseModerateableEntity#beforeStateChange(
     *       ModerationState, ModerationState) */
    @Override
    protected final void beforeStateChange(final ModerationState actualState,
            final ModerationState newState) {
        getModerationEntryRepository().notifyChange(this, actualState, newState);
    }
    
}

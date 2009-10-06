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

import java.util.List;

import org.apache.commons.lang.Validate;

/**
 * Entidades moderables que obtienen su información de moderación 
 * de un {@link BaseRepositoryModerateableEntity}
 * 
 * @author Pablo Grigolatto
 * @since Oct 6, 2009
 */
public abstract class BaseRepositoryModerateableEntity 
    extends BaseModerateableEntity {

    private ModerationEntryRepository moderationRepository;

    /** Constructor */
    public BaseRepositoryModerateableEntity(
            final ModerationState state,
            final ModerationEntryRepository moderationRepository) {
        super(state);
        
        Validate.notNull(moderationRepository);
        this.moderationRepository = moderationRepository;
    }
    
    /** @see Moderateable#getModerationHistory() */
    public final List<ModerationEntry> getModerationHistory() {
        return moderationRepository.getModerationEntries(this);
    }
   
    /** @see BaseModerateableEntity#beforeStateChange(
     *       ModerationState, ModerationState) */
    @Override
    protected final void beforeStateChange(final ModerationState actualState,
            final ModerationState newState) {
        moderationRepository.notifyChange(this, actualState, newState);
    }
    
}

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

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.moderation.Moderateable;
import ar.com.zauber.commons.moderation.ModerationEntryRepository;
import ar.com.zauber.commons.moderation.ModerationState;
import ar.com.zauber.commons.moderation.BaseRepositoryModerateableEntity;
import ar.com.zauber.commons.repository.Persistible;

/**
 * Implementacion para test
 * 
 * @author Pablo Grigolatto
 * @since Oct 6, 2009
 */
public class MockRespositoryModerateableEntity 
    extends BaseRepositoryModerateableEntity implements Moderateable {

    private final Long id;

    /** Constructor */
    public MockRespositoryModerateableEntity(final Long id, 
            final ModerationState state,
            final ModerationEntryRepository moderationRepository) {
        super(state, moderationRepository);
        
        Validate.notNull(id);
        this.id = id;
    }

    /** @see Persistible#getId() */
    public final Long getId() {
        return id;
    }

    /** @see Persistible#setId(Long) */
    public final void setId(final Long anId) {
        throw new NotImplementedException();
    }
    
}

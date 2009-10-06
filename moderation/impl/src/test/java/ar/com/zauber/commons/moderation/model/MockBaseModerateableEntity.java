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

import ar.com.zauber.commons.moderation.BaseModerateableEntity;
import ar.com.zauber.commons.moderation.ModerationEntry;
import ar.com.zauber.commons.moderation.ModerationState;
import ar.com.zauber.commons.repository.Persistible;

/**
 * Implementacion para test
 * 
 * @author Pablo Grigolatto
 * @since Oct 6, 2009
 */
public class MockBaseModerateableEntity extends BaseModerateableEntity {

    /** Creates the MockModerateableEntity */
    public MockBaseModerateableEntity(final ModerationState moderationState) {
        super(moderationState);
    }

    /** @see BaseModerateableEntity#getModerationHistory() */
    public final List<ModerationEntry> getModerationHistory() {
        throw new NotImplementedException();
    }

    /** @see Persistible#getId() */
    public final Long getId() {
        throw new NotImplementedException();
    }

    /** @see Persistible#setId(Long) */
    public final void setId(final Long anId) {
        throw new NotImplementedException();
    }
    
}
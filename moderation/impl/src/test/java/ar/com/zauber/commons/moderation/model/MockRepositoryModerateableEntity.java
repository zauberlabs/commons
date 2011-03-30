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
package ar.com.zauber.commons.moderation.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang.Validate;
import org.hibernate.annotations.TypeDef;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;

import ar.com.zauber.commons.moderation.BaseRepositoryModerateableEntity;
import ar.com.zauber.commons.moderation.ModerationEntryRepository;
import ar.com.zauber.commons.moderation.ModerationState;
import ar.com.zauber.commons.repository.Persistible;

/**
 * Implementacion para test
 * 
 * @author Pablo Grigolatto
 * @since Oct 6, 2009
 */
@Entity
@Configurable
@TypeDef(name = "type_moderationState", typeClass = EnumModerationState.class)
public class MockRepositoryModerateableEntity 
    extends BaseRepositoryModerateableEntity {

    @Id
    @GeneratedValue
    private Long id;
    @Qualifier
    private transient ModerationEntryRepository moderationEntryRepository;
    
    /** Constructor */
    private MockRepositoryModerateableEntity() {
        // default
    }
    
    /** Constructor */
    public MockRepositoryModerateableEntity(final Long id, 
            final ModerationState state,
            final ModerationEntryRepository moderationEntryRepository) {
        super(state);
        
        Validate.notNull(id);
        Validate.notNull(moderationEntryRepository);
        this.id = id;
        this.moderationEntryRepository = moderationEntryRepository;
    }

    /** Constructor */
    public MockRepositoryModerateableEntity(final ModerationState state,
            final ModerationEntryRepository moderationRepository) {
        super(state);
        
        Validate.notNull(moderationRepository);
        this.moderationEntryRepository = moderationRepository;
    }

    /** @see BaseRepositoryModerateableEntity#getModerationEntryRepository() */
    protected final ModerationEntryRepository getModerationEntryRepository() {
        return moderationEntryRepository;
    }
    
    /** @see Persistible#getId() */
    public final Long getId() {
        return id;
    }

    /** @see Persistible#setId(Long) */
    public final void setId(final Long anId) {
        Validate.notNull(anId);
        id = anId;
    }
    
}

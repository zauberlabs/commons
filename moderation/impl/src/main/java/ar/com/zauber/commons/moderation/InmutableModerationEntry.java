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

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.hibernate.annotations.Type;

import ar.com.zauber.commons.repository.BaseCreationAuditableEntity;
import ar.com.zauber.commons.repository.Persistible;
import ar.com.zauber.commons.repository.Reference;

/**
 * Implementación inmutable de {@link ModerationEntry}
 * 
 * @author Pablo Grigolatto
 * @since Oct 6, 2009
 */
@MappedSuperclass
@Table(name = "moderation_states_history")
public class InmutableModerationEntry extends BaseCreationAuditableEntity
    implements ModerationEntry {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "id", column = @Column(name = "ref_id")),
            @AttributeOverride(name = "clazzName", 
                    column = @Column(name = "ref_class_name")),
            @AttributeOverride(name = "version", 
                    column = @Column(name = "ref_version"))
    })
    private Reference<Moderateable> reference;
    
    /** El tipo 'type_moderationState' debe ser especificado en las subclases */
    @Type(type = "type_moderationState")
    private ModerationState initialState;
    @Type(type = "type_moderationState")
    private ModerationState finalState;
    
    @Column(nullable = false)
    private Date moderatedAt;
    @Column(nullable = false)
    private String moderatedBy;

    /** Constructor */
    protected InmutableModerationEntry() {
        // default
    }
    
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

    /** @see Persistible#getId() */
    public final Long getId() {
        return id;
    }

    /** @see Persistible#setId(Long) */
    public final void setId(final Long anId) {
        id = anId;
    }
    
}

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
package ar.com.zauber.commons.repository;

import java.util.Date;

import javax.persistence.MappedSuperclass;

/**
 * Clase base para entidades que necesiten auditoria de modificación.
 * 
 * @author Martín Andrés Márquez
 * @since Nov 12, 2007
 */
@MappedSuperclass
public abstract class BaseCreationModificationAuditableEntity 
                           extends BaseModifiableEntity 
                           implements CreationAuditable, ModificationAuditable {

    private Date createdAt;
    private String createdBy;
    private Date modifiedAt;
    private String modifiedBy;

    /** Creates the BaseCreationModificationAuditableEntity. */
    public BaseCreationModificationAuditableEntity() {
        // void
    }
    
    /**
     * 
     * Creates the BaseCreationModificationAuditableEntity.
     *
     * @param createdAt  creation date
     * @param createdBy  creation username
     * @param modifiedAt modification date
     * @param modifiedBy modification username
     */
    public BaseCreationModificationAuditableEntity(
            final Date createdAt, final String createdBy, 
            final Date modifiedAt, final String modifiedBy) {
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
    }
    
    public final Date getCreatedAt() {
        return createdAt;
    }

    public final String getCreatedBy() {
        return createdBy;
    }

    
    public final Date getModifiedAt() {
        return modifiedAt;
    }

    public final String getModifiedBy() {
        return modifiedBy;
    }
}

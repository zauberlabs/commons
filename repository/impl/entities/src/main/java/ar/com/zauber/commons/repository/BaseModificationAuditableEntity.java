/**
 * Copyright (c) 2005-2012 Zauber S.A. <http://www.zaubersoftware.com/>
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
public abstract class BaseModificationAuditableEntity 
    extends BaseModifiableEntity implements ModificationAuditable {

    private Date modifiedAt;
    private String modifiedBy;

    /** Creates the BaseModificationAuditableEntity. */
    public BaseModificationAuditableEntity() {
        // void
    }
    
    /** Creates the BaseModificationAuditableEntity. */
    public BaseModificationAuditableEntity(final Date modifiedAt, 
            final String modifiedBy) {
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
    }
    
    public final Date getModifiedAt() {
        return modifiedAt;
    }

    public final String getModifiedBy() {
        return modifiedBy;
    }
}

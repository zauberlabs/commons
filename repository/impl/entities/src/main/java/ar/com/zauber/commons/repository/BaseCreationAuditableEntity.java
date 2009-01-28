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

import org.apache.commons.lang.Validate;

/**
 * Clase base para entidades que necesiten auditoria de creaci�n.
 * 
 * @author Mart�n Andr�s M�rquez
 * @since Nov 12, 2007
 */
@MappedSuperclass
public abstract class BaseCreationAuditableEntity extends BaseEntity 
                                      implements CreationAuditable {
    private Date createdAt;
    private String createdBy;
    
    /** default constructor */
    public BaseCreationAuditableEntity() {
        // void!
    }
    
    /**
     * Creates the BaseCreationAuditableEntity.
     *
     * @param createdAt creation date
     * @param createdBy creation username
     */
    public BaseCreationAuditableEntity(final Date createdAt,
            final String createdBy) {
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        
    }
    
    public final Date getCreatedAt() {
        return createdAt;
    }

    public final String getCreatedBy() {
        return createdBy;
    }

}

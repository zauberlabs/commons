/*
 * Copyright (c) 2005-2008 Zauber S.A. <http://www.zauber.com.ar/>
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
public abstract class BaseCreationModificationAuditableEntity extends BaseModifiableEntity 
                           implements CreationAuditable, ModificationAuditable {

    /** <code>createdAt</code> */
    private Date createdAt;
    /** <code>createdBy</code> */
    private String createdBy;
    /** <code>modifiedAt</code> */
    private Date modifiedAt;
    /** <code>modifiedBy</code> */
    private String modifiedBy;
    
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

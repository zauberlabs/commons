/*
 * Copyright (c) 2005 Zauber -- All rights reserved
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
public class BaseCreationModificationAuditableEntity extends BaseModifiableEntity implements CreationAuditable, ModificationAuditable {

    /** <code>createdAt</code> */
    private Date createdAt;
    /** <code>createdBy</code> */
    private String createdBy;
    /** <code>modifiedAt</code> */
    private Date modifiedAt;
    /** <code>modifiedBy</code> */
    private String modifiedBy;
    
    public Date getCreatedAt() {
        return createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    
    public Date getModifiedAt() {
        return modifiedAt;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

}

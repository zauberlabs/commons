/*
 * Copyright (c) 2005 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.repository;

import java.util.Date;

import javax.persistence.MappedSuperclass;

/**
 * Clase base para entidades que necesiten auditoria de modificaci�n.
 * 
 * @author Mart�n Andr�s M�rquez
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

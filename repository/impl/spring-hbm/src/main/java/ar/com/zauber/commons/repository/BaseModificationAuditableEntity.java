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
public abstract class BaseModificationAuditableEntity extends BaseModifiableEntity implements ModificationAuditable {

    
    private Date modifiedAt;
    private String modifiedBy;
    
    public Date getModifiedAt() {
        return modifiedAt;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }


}

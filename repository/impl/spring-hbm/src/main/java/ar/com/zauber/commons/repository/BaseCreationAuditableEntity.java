/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.repository;

import java.util.Date;

import javax.persistence.MappedSuperclass;

/**
 * Clase base para entidades que necesiten auditoria de creación.
 * 
 * @author Martín Andrés Márquez
 * @since Nov 12, 2007
 */
@MappedSuperclass
public abstract class BaseCreationAuditableEntity extends BaseEntity 
                                      implements CreationAuditable {
    private Date createdAt;
    private String createdBy;
    
    public final Date getCreatedAt() {
        return createdAt;
    }

    public final String getCreatedBy() {
        return createdBy;
    }

}

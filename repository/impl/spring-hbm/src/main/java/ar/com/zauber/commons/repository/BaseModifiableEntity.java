/*
 * Copyright (c) 2005 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.repository;

import javax.persistence.MappedSuperclass;

/**
 * Clase base para entidades que pueden ser modificadas.
 * 
 * 
 * @author Martín Andrés Márquez
 * @since Nov 12, 2007
 */
@MappedSuperclass
public abstract class BaseModifiableEntity extends BaseEntity implements Modifiable {

    private Long version;
    
    public final Long getVersion() {
        return version;
    }
    
    public final Reference generateReference() {
        return new Reference(this.getClass(), getId(), getVersion());
    }

}

/*
 * Copyright (c) 2005 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.repository;

import javax.persistence.MappedSuperclass;

/**
 * Clase base para entidades que pueden ser modificadas.
 * 
 * 
 * @author Mart�n Andr�s M�rquez
 * @since Nov 12, 2007
 */
@MappedSuperclass
public class BaseModifiableEntity extends BaseEntity implements Modifiable {

    private Long version;
    
    public Long getVersion() {
        return version;
    }
    
    public Reference generateReference() {
        return new Reference(this.getClass(), getId(), getVersion());
    }

}
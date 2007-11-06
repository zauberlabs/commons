/*
 * Copyright (c) 2005 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.repository;

import java.io.Serializable;

/**
 * Representa una referencia a un objeto de dominio. La clase encapsual un
 * nombre de clase, un identificador y una version del objeto. La version se
 * utiliza para chequear concurrencia. Cuando se quiere hacer un update de un
 * objeto en mas de una sesion en forma simultanea.
 * 
 * @author Martin Andres Marquez
 */
public class Reference<T> implements Serializable {

    /** <code>id</code> */
    private Long id;

    /** <code>version</code> */
    private long version;

    /** <code>clazz</code> */
    private Class<T> clazz;
    
    /**
     * Creates the Reference.
     * 
     * @param id
     *            the id
     * @param className
     *            the class name
     */
    public Reference(final Class<T> clazz, final Long id) {
        this(clazz);
        this.id = id;
    }
    
    /**
     * Creates the Reference.
     *
     * @param className
     * @param id
     * @param version
     */
    public Reference(final Class<T> clazz, final Long id, final long version) {
        this(clazz, id);
        this.version = version;
    }
    
    /**
     * Creates the Reference.
     * 
     */
    public Reference(Class<T> clazz) {
        this.clazz = clazz;
        this.version = -1;
    }

    /**
     * Returns the id.
     * 
     * @return <code>Serializable</code> with the id.
     */
    public final Long getId() {
        return this.id;
    }

    /**
     * Returns the version.
     * 
     * @return <code>long</code> with the version.
     */
    public final long getVersion() {
        return this.version;
    }

    /**
     * Returns the className.
     * 
     * @return <code>String</code> with the className.
     */
    public final String getClassName() {
        return clazz.getName();
    }

    /** @see java.lang.Object#toString() */
    public final String toString() {
        return getClassName() + "@" + getId();
    }

}

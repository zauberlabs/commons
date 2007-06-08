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
public class Reference implements Serializable {

    /** <code>id</code> */
    private Long id;

    /** <code>className</code> */
    private String className;

    /** <code>version</code> */
    private long version;

    /**
     * Creates the Reference.
     * 
     * @param id
     *            the id
     * @param className
     *            the class name
     */
    public Reference(final String className, final Long id) {
        this(className);
        this.id = id;
    }

    /**
     * Creates the Reference.
     * 
     * @param id
     *            the id
     * @param clazz
     *            the class
     */
    public Reference(final Class clazz, final Long id) {
        this(clazz);
        this.id = id;
    }

    /**
     * Creates the Reference.
     * 
     * @param className
     *            the class name
     */
    public Reference(final String className) {
        this.className = className;
        this.version = -1;
    }

    /**
     * Creates the Reference.
     * 
     * @param clazz
     *            the class
     */
    public Reference(final Class clazz) {
        this.className = clazz.getName();
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
     * Sets the id.
     * 
     * @param id
     *            <code>Serializable</code> with the id.
     */
    public final void setId(final Long id) {
        this.id = id;
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
     * Sets the version.
     * 
     * @param version
     *            <code>long</code> with the version.
     */
    public final void setVersion(final long version) {
        this.version = version;
    }

    /**
     * Returns the className.
     * 
     * @return <code>String</code> with the className.
     */
    public final String getClassName() {
        return this.className;
    }

    /** @see java.lang.Object#toString() */
    public final String toString() {
        return getClassName() + "@" + getId();
    }

}

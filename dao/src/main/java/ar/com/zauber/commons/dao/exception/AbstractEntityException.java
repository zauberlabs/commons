/*
 * Copyright (c) 2005 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.dao.exception;


/**
 * Base class for Entity exceptions. 
 * 
 * Está pensada para ser extendida. Tal vez resulte raro extender la clase
 * para sólamente cambiar la semantica (y no el comportamiento!). Es esto o
 * tener un enum en el constructor. Creemos en la elegancia de está solucion
 * 
 * TODO comentario a ingles, y mas detallado
 * 
 * @author Juan F. Codagnone
 * @author Fernando J. Zunino
 * @since Sep 1, 2005
 */
public abstract class AbstractEntityException extends RuntimeException {
    /** the entity that wasn't found */
    private final Object entity;

    /**
     * Creates the NoSuchEntityException.
     * 
     * @param entity
     *            the entity that wasn't found
     * @param th
     *            the cause
     */
    public AbstractEntityException(final Object entity, final Throwable th) {
        super(th);
        this.entity = entity;
    }

    /**
     * Creates the NoSuchEntityException.
     * 
     * @param entity
     *            the entity that wasn't found
     */
    public AbstractEntityException(final Object entity) {
        super();
        this.entity = entity;
    }

    /**
     * @return the entity that wasn't found
     */
    public final Object getEntity() {
        return entity;
    }

}

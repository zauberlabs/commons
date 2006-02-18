/*
 * Copyright (c) 2005 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.exception;

/**
 * Excepción indicando que no existe la entidad. [ Basada en el
 * NoSuchEntityException de Mariano de conae, nada mas que lo pasé a Runtime.
 * Seria interesante hacer tipada a la exception <T>, y poder cachear la
 * excepcion esperando un tipo especifico, pero el compilador no me deja --
 * wiki:Main.JuanCodagnone Sun Jun 19 00:32:02 ART 2005 ]
 * 
 * @since Jun 19, 2005
 */
public class NoSuchEntityException extends AbstractEntityException {
    /**
     * @see AbstractEntityException#AbstractEntityException(Object, Throwable)
     */
    public NoSuchEntityException(final Object entity, final Throwable th) {
        super(entity, th);
    }

    /**
     * @see AbstractEntityException#AbstractEntityException(Object)
     */
    public NoSuchEntityException(final Object entity) {
        super(entity);
    }
}

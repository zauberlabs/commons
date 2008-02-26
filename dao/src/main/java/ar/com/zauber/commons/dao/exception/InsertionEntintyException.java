/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.dao.exception;



/**
 * TODO Brief description.
 *
 * TODO Detail
 *
 * @author Juan F. Codagnone
 * @since Feb 20, 2006
 */
public class InsertionEntintyException extends AbstractEntityException {

    /**
     * Creates the InsertionEntintyException.
     *
     * @param object the object not found
     * @param t  the cause of the exception
     */
    public InsertionEntintyException(final Object object, final Throwable t) {
        super(object, t);
    }

}

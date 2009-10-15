/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.exception.triggered;

import java.util.Collection;
import java.util.LinkedList;

import org.apache.commons.lang.Validate;

/**
 * 
 * 
 * 
 * @author Mariano Semelman
 * @since Oct 13, 2009
 */
public class TriggeredException extends RuntimeException {

    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1L;
    private Collection<Throwable> exceptions;
    
    
    /**
     * Creates the TriggeredException.
     *
     * @param list
     */
    public TriggeredException(final Collection<Throwable> exceptions) {
        super();
        Validate.notNull(exceptions);
        this.exceptions = exceptions;
    }


    /** constructor por defecto */
    public TriggeredException() {
        exceptions = new LinkedList<Throwable>();
    }
    
    /** @see java.lang.Throwable#getMessage() */
    public final String getMessage() {
        String message = "The following Exceptions ocurred:\n";
        for(Throwable t : exceptions) {
            message += t.getMessage() + "\n";
        }
        return message;
    }
    
}

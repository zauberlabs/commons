/*
 * Copyright (c) 2011 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.dao.closure;

import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Closure;

/**
 * A closure that throws an exception. 
 * 
 * @author Guido Marucci Blas
 * @since Feb 10, 2011
 */
public class FatalClosure<T> implements Closure<T>{

    private final Throwable e;
    
    /**
     * Creates the FatalClosure.
     *
     */
    public FatalClosure(final Throwable e) {
        Validate.notNull(e);
        
        this.e = e;
    }
    
    public void execute(T t) {
        throw new UnhandledException(e);
    };
}

/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.dao.closure.wrapper;

import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.commons.dao.closure.ClosureWrapperFactory;
import ar.com.zauber.commons.dao.closure.ErrorLoggerWrapperClosure;

/**
 * Crea un {@link ErrorLoggerWrapperClosure}
 * 
 * @author Juan F. Codagnone
 * @since Oct 29, 2010
 */
public class ErrorLoggerClosureWrapperFactory<T> implements ClosureWrapperFactory<T> {
    private final Closure<Throwable> errorClosure;
    
    /** Creates the ErrorLoggerClosureWrapperFactory. */
    public ErrorLoggerClosureWrapperFactory() {
        this(null);
    }
    
    /** Creates the ErrorLoggerClosureWrapperFactory. */
    public ErrorLoggerClosureWrapperFactory(final Closure<Throwable> errorClosure) {
        this.errorClosure = errorClosure;
    }
    
    @Override
    public Closure<T> decorate(final Closure<T> target) {
        return new ErrorLoggerWrapperClosure<T>(target, errorClosure);
    }
}

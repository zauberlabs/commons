/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.dao.closure.processors;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.commons.dao.ClosureProcessor;

/**
 * {@link ClosureProcessor} que obtiene todos los elementos a procesar de un
 * iterable. Es muy util para pruebas. 
 * 
 * @author Matías G. Tito
 * @since Sep 29, 2009
 * @param <T> Tipo de entidad
 */
public class IterableClosureProcesor<T> implements ClosureProcessor<T> {
    private final Iterable<T> source;

    /** Creates the IterableClosureProcesor. */
    public IterableClosureProcesor(final Iterable<T> source) {
        Validate.notNull(source);
        
        this.source = source;
    }
    
    /** @see ClosureProcessor#process(Closure) */
    public final void process(final Closure<T> closure) {
        Validate.notNull(closure);
        for(final T entry : source) {
            closure.execute(entry);
        }
    }
}

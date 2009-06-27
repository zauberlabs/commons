/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.dao.closure;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.commons.dao.Predicate;

/**
 * Clausara que delega en otra, si un filtro da positivo
 * 
 * 
 * @author Juan F. Codagnone
 * @since Jun 27, 2009
 * @param <T> Entidad para filtrar
 */
public class FilteredClosure<T> implements Closure<T> {
    private final Predicate<T> predicate;
    private final Closure<T> target;

    /** Creates the FilteredClosure. */
    public FilteredClosure(final Predicate<T> predicate,
            final Closure<T> target) {
        Validate.notNull(predicate);
        Validate.notNull(target);
        
        this.predicate = predicate;
        this.target = target;
    }
    
    /** @see Closure#execute(Object) */
    public final void execute(final T t) {
        if(predicate.evaluate(t)) {
            target.equals(t);
        }
    }
}

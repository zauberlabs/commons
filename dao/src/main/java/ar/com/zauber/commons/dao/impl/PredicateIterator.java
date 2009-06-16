/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.dao.impl;

import java.util.Iterator;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Predicate;

/**
 * Iterador que filtra el contenido de otro iterador `on the fly'.
 * 
 * @author Juan F. Codagnone
 * @since Jun 16, 2009
 * @param <T> type
 */
public class PredicateIterator<T> implements Iterator<T> {
    private final Iterator<T> target;
    private final Predicate<T> predicate;
    private T current;

    /**
     * Creates the PredicateIterator.
     *
     * @param target     iterador posta
     * @param predicate  el predicado utilizado para filtrar
     */
    public PredicateIterator(final Iterator<T> target,
            final Predicate<T> predicate) {
        Validate.notNull(target);
        Validate.notNull(predicate);
        
        this.target = target;
        this.predicate = predicate;
        
        next();
    }
    
    /** @see Iterator#hasNext() */
    public final boolean hasNext() {
        return current != null;
    }

    /** @see Iterator#next() */
    public final T next() {
        T ret = current;
        current = null;
        while(target.hasNext()) {
            current = target.next();
            if(predicate.evaluate(current)) {
                break;
            } else {
                current = null;
            }
        }
        
        return ret;
    }

    /** @see Iterator#remove() */
    public final void remove() {
        throw new UnsupportedOperationException("remove() not supported");
    }

}

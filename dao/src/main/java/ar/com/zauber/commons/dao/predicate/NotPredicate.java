/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.dao.predicate;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Predicate;

/**
 * {@link Predicate} que delega a otro y niega su respuesta.
 * 
 * @author Juan F. Codagnone
 * @since Jun 27, 2009
 * @param <T> entity type
 */
public class NotPredicate<T> implements Predicate<T> {
    private final Predicate<T> predicate;

    /** Creates the NotPredicate. */
    public NotPredicate(final Predicate<T> predicate) {
        Validate.notNull(predicate);
        
        this.predicate = predicate;
    }
    
    /** @see Predicate#evaluate(java.lang.Object) */
    public final boolean evaluate(final T value) {
        return !predicate.evaluate(value);
    }
}

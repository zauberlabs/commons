/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.dao.closure;

import java.util.Map.Entry;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.commons.dao.Predicate;

/**
 * @see SwitchClosure.
 * 
 * @author Juan F. Codagnone
 * @since Jan 22, 2010
 * @param <T> type
 */
public class PredicateClosureEntry<T> implements Entry<Predicate<T>, Closure<T>> {
    private final Predicate<T> predicate;
    private final Closure<T> closure;

    /** Creates the PredicateClosureEntry. */
    public PredicateClosureEntry(final Predicate<T> predicate, 
            final Closure<T> closure) {
        Validate.notNull(predicate);
        Validate.notNull(closure);
        
        this.predicate = predicate;
        this.closure = closure;
    }
    
    /** @see Entry#getKey() */
    public final Predicate<T> getKey() {
        return predicate;
    }

    /** @see Entry#getValue() */
    public final Closure<T> getValue() {
        return closure;
    }

    /** @see Entry#setValue(Object) */
    public final Closure<T> setValue(final Closure<T> value) {
        throw new UnsupportedOperationException();
    }

}

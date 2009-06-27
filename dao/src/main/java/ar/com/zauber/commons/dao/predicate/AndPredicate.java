/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.dao.predicate;

import java.util.List;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Predicate;

/**
 * predicate1 And predicate2 And Predicate3 ...
 * 
 * @author Juan F. Codagnone
 * @since Jun 27, 2009
 * @param <T> predicate
 */
public final class AndPredicate<T> implements Predicate<T> {
    private final List<Predicate<T>> predicates;

    /** constructor */
    public AndPredicate(final List<Predicate<T>> predicates) {
        Validate.noNullElements(predicates);
        
        this.predicates = predicates;
    }
    
    /** @see Predicate#evaluate(Object) */
    public boolean evaluate(final T value) {
        boolean ret = true;
        
        for(final Predicate<T> predicate : predicates) {
            ret &= predicate.evaluate(value);
        }
        
        return ret;
    }
}

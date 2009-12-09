/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.dao.closure;

import ar.com.zauber.commons.dao.Closure;

/**
 * Closures pre-hechos
 * 
 * @author Juan F. Codagnone
 * @since Dec 9, 2009
 */
public final class Closures {
    private static final Closure<?> NULL_CLOSURE = new NullClosure();
    
    /** utility class  */
    private Closures() {
        //void
    }
    
    /** @return un {@link NullClosure}. */
    @SuppressWarnings("unchecked")
    public static <T> Closure<T> nullClosure() {
        return (Closure<T>) NULL_CLOSURE;
    }
}

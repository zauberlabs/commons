/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.dao.impl;

import ar.com.zauber.commons.dao.Predicate;

/**
 * Predicate that always return true.
 * 
 * @author Juan F. Codagnone
 * @since Jun 16, 2009
 */
public class TruePredicate<T> implements Predicate<T> {

    /** @see Predicate#evaluate(Object) */
    public final boolean evaluate(final T value) {
        return true;
    }

}

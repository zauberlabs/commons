/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.dao.impl;

import ar.com.zauber.commons.dao.Predicate;

/**
 * Predicate that always returns false.
 * 
 * @author Juan F. Codagnone
 * @since Jun 16, 2009
 */
public class FalsePredicate<T> implements Predicate<T> {

    /** @see Predicate#evaluate(java.lang.Object) */
    public boolean evaluate(final T value) {
        return false;
    }
}

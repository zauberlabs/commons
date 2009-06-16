/*
 * Copyright (c) 2008 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.dao.impl;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Predicate;

/**
 * Equals that ignore case predicate 
 * 
 * 
 * @author Juan F. Codagnone
 * @since Jul 12, 2008
 */
public class IgnoreCaseEqualsPredicate implements Predicate<String> {
    private final String target;

    /** Creates the EqualsPredicate. */
    public IgnoreCaseEqualsPredicate(final String target) {
        Validate.notNull(target);
        this.target = target;
    }
    
    /** @see Predicate#evaluate(java.lang.Object) */
    public final boolean evaluate(final String value) {
        return target.equalsIgnoreCase(value);
    }
}

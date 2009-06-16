/*
 * Copyright (c) 2008 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.dao.impl;

import junit.framework.TestCase;
import ar.com.zauber.commons.dao.impl.EqualsPredicate;
import ar.com.zauber.commons.dao.impl.IgnoreCaseEqualsPredicate;

/**
 * Test
 * 
 * @author Juan F. Codagnone
 * @since Jul 12, 2008
 */
public class IgnoreCaseEqualsPredicateTest extends TestCase {

    /** test */
    public final void testTrue() {
        assertTrue(new IgnoreCaseEqualsPredicate("abc").evaluate("ABC"));
        assertTrue(new IgnoreCaseEqualsPredicate("abc").evaluate("abc"));
    }
    
    /** test */
    public final void testFalse() {
        assertFalse(new IgnoreCaseEqualsPredicate("abc").evaluate("asd"));
    }
    
    /** test */
    public final void testNull() {
        assertFalse(new EqualsPredicate("abc").evaluate(null));
    }
    
    /** test */
    public final void testNullTarget() {
        try {
            new EqualsPredicate(null);
            fail();
        } catch(IllegalArgumentException e) {
            // ok
        }
    }
}

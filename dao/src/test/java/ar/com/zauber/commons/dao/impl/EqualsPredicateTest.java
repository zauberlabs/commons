/*
 * Copyright (c) 2008 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.dao.impl;

import junit.framework.TestCase;
import ar.com.zauber.commons.dao.impl.EqualsPredicate;

/**
 * Test
 * 
 * @author Juan F. Codagnone
 * @since Jul 12, 2008
 */
public class EqualsPredicateTest extends TestCase {

    /** test */
    public final void testTrue() {
        assertTrue(new EqualsPredicate("abc").evaluate("abc"));
    }
    
    /** test */
    public final void testFalse() {
        assertFalse(new EqualsPredicate("abc").evaluate("ABC"));
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

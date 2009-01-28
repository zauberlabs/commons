/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.dao;

import junit.framework.TestCase;

/**
 * Tests Order
 * 
 * 
 * @author Juan F. Codagnone
 * @since Jan 28, 2009
 */
public class OrderTest extends TestCase {

    /** test */
    public final void testSimpleConstructor() {
        final Order o = new Order("description");
        assertEquals("description", o.getProperty());
        assertTrue(o.getAscending());
        assertFalse(o.isIgnoreCase());
    }
    
    /** test */
    public final void testAscendingConstructor() {
        Order o = new Order("description", true);
        assertEquals("description", o.getProperty());
        assertTrue(o.getAscending());
        assertFalse(o.isIgnoreCase());
        
        o = new Order("description", false);
        assertEquals("description", o.getProperty());
        assertFalse(o.getAscending());
        assertFalse(o.isIgnoreCase());
    }
}

/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.dao;

import org.junit.Assert;
import org.junit.Test;


/**
 * Test Ordering
 * 
 * 
 * @author Juan F. Codagnone
 * @since Aug 7, 2009
 */
public class OrderingTest {

    /** test secure constructor */
    @Test
    public final void constructorEmpty() {
        final Ordering ordering = new Ordering();
        Assert.assertTrue(ordering.getOrders().size()  == 0);
    }
    
    /** test inmutability */
    @Test
    public final void inmutable() {
        final Ordering ordering = new Ordering();
        try {
            ordering.getOrders().add(new Order("username"));
            Assert.fail();
        } catch (UnsupportedOperationException e) {
            // ok
        }
    }
    
    /** test inmutability */
    @Test
    public final void tostring() {
        final Ordering ordering = new Ordering(new Order("user"), 
                new Order("creation", false, true));
        Assert.assertEquals("[user ASC, creation DESC ignoring case]", 
                ordering.toString());
    }
    
    /** equals */
    @Test
    public final void equals() {
        final Ordering o1 = new Ordering(new Order("user"), 
                new Order("creation", false, true));
        final Ordering o2 = new Ordering(new Order("user"), 
                new Order("creation", false, true));
        final Ordering o3 = new Ordering(new Order("name"), 
                new Order("creation", false, true));
        
        Assert.assertEquals(o1, o1);
        Assert.assertEquals(o1, o2);
        Assert.assertEquals(o1.hashCode(), o2.hashCode());
        Assert.assertFalse(o1.equals(o3));
    }
}

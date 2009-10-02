/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.dao.impl;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

import ar.com.zauber.commons.dao.RankeableResult;


/**
 * Tests para {@link InmutableRankeableResult}
 * 
 * @author Juan Almeyda
 * @since Oct 2, 2009
 */
public class InmutableRankeableResultTest {

    /** test del equals*/
    @Test
    public final void testEqualsYHashCode() {
        final RankeableResult<String> rr1 = new InmutableRankeableResult<String>(1, "jaja");
        final RankeableResult<String> rr2 = new InmutableRankeableResult<String>(1, "jaja");
        Assert.assertEquals(rr1, rr1);
        Assert.assertFalse(rr1.equals(null));
        Assert.assertEquals(rr1, rr2);
        Assert.assertEquals(rr1.hashCode(), rr2.hashCode());
        Assert.assertEquals(rr1.hashCode(), rr1.hashCode());
    }
    
    /** test de not equals*/
    @Test
    public final void testNotEquals() {
        final RankeableResult<String> rr1 =  new InmutableRankeableResult<String>(99, "armando");
        final RankeableResult<String> rr2 =  new InmutableRankeableResult<String>(99.5, "armando");
        final RankeableResult<String> rr3 =  new InmutableRankeableResult<String>(99.5, "paredes");
        assertNotSame(rr1, rr2);
        assertNotSame(rr3, rr2);
        assertNotSame(rr3, rr1);
        assertNotSame(rr1.hashCode(), rr2.hashCode());
        assertNotSame(rr1.hashCode(), rr3.hashCode());
        assertNotSame(rr3.hashCode(), rr2.hashCode());
    }
    
}

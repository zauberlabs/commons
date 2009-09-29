/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.dao;

import org.junit.Assert;
import org.junit.Test;


/**
 * Test {@link OrderingBuilder}.
 * 
 * @author Juan F. Codagnone
 * @since Aug 7, 2009
 */
public class OrderingBuilderTest {

    /**
     *  test the construction of a new {@link Ordering} using 
     * {@link OrderingBuilder} 
     */
    @Test
    public final void build() throws Exception {
        final Ordering ordering = Ordering.builder()
                             .asc("name")
                             .ascIgnoringCase("username")
                             .desc("points")
                             .descIgnoringCase("foo")
                             .ordering();
        
        final Ordering expected = new Ordering(
                new Order("name"), 
                new Order("username", true, true),
                new Order("points", false), 
                new Order("foo", false, true));
        
        Assert.assertEquals(expected, ordering);
    }
}

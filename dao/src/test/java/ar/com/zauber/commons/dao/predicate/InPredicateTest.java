/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.dao.predicate;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import ar.com.zauber.commons.dao.Predicate;


/**
 * Tests the {@link InPredicate} 
 * 
 * @author Christian Nardi
 * @since Oct 30, 2009
 */
public class InPredicateTest {
    /**
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        final Predicate<String> predicate = 
            new InPredicate<String>(Arrays.asList(
                    new String[]{"hello", "bye", "hey!"}));
        Assert.assertTrue(predicate.evaluate("hello"));
        Assert.assertTrue(predicate.evaluate("bye"));
        Assert.assertTrue(predicate.evaluate("hey!"));
        Assert.assertFalse(predicate.evaluate("hum?"));
    }
}

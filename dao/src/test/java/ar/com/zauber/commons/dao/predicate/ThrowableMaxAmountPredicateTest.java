/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.dao.predicate;

import java.util.Collection;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;

import ar.com.zauber.commons.dao.Predicate;


/**
 * Testeo del predicado maxAmountPredicate
 * 
 * @author Mariano Semelman
 * @since Oct 15, 2009
 */
public class ThrowableMaxAmountPredicateTest {
    
    /** test para probar el predicado*/
    @Test
    public final void test() {
        Predicate<Collection<Throwable>> maxAmountPredicate = 
            new ThrowableMaxAmountPredicate(3);
        Collection<Throwable> throwables = new LinkedList<Throwable>();
        for(int x = 0; x <= 3; x++) {
            Assert.assertFalse(maxAmountPredicate.evaluate(throwables));
            throwables.add(new IllegalArgumentException());
        }
        Assert.assertTrue(maxAmountPredicate.evaluate(throwables));
        
       try {
           maxAmountPredicate.evaluate(null);
           Assert.fail();
       } catch(IllegalArgumentException e) {
           //
       }
        
    }

}

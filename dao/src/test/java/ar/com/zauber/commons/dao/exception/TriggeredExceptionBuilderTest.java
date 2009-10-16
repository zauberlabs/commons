/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.dao.exception;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import ar.com.zauber.commons.dao.Predicate;
import ar.com.zauber.commons.dao.exception.TriggeredException;
import ar.com.zauber.commons.dao.exception.TriggeredExceptionBuilder;
import ar.com.zauber.commons.dao.predicate.ThrowableMaxAmountPredicate;


/**
 * TODO Descripcion de la clase. Los comentarios van en castellano.
 * 
 * @author Mariano Semelman
 * @since Oct 15, 2009
 */
public class TriggeredExceptionBuilderTest {

    /** test general */
    @Test
    public final void test() {
        Predicate<Collection<Throwable>> condition = 
            new ThrowableMaxAmountPredicate(3);
        TriggeredExceptionBuilder builder = new TriggeredExceptionBuilder(condition);
        for(int x = 0; x < 3; x++) {
            builder.add(new IllegalArgumentException());
        }
        try {
            builder.add(new IllegalArgumentException());
            Assert.fail();
        } catch(TriggeredException e) {
            Assert.assertEquals(4, builder.getThrowablesOcurred().size());
        }
    }
}

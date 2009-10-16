/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.dao.exception;

import java.util.Collection;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;


/**
 * TODO Descripcion de la clase. Los comentarios van en castellano.
 * 
 * 
 * @author Mariano Semelman
 * @since Oct 16, 2009
 */
public class TriggeredExceptionTest {

    /** test */
    @Test 
    public final void test() {
        Throwable exc = new TriggeredException();
        Assert.assertTrue(exc.toString().contains("TriggeredException"));
        Collection<Throwable> throwables = new LinkedList<Throwable>();
        throwables.add(new IllegalArgumentException());
        exc = new TriggeredException(throwables);
        boolean todos = true;
        boolean entro = false;
        for(Throwable t : throwables) {
            todos = todos && exc.toString().contains(t.toString());
            entro = true;
        }
        Assert.assertTrue(todos);
        Assert.assertTrue(entro);
    }
}

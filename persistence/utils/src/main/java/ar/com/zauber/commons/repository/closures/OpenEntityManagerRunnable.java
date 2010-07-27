/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.repository.closures;

import javax.persistence.EntityManagerFactory;

import ar.com.zauber.commons.dao.Closure;

/**
 * Similar a {@link OpenEntityManagerClosure}.
 * 
 * @author Juan F. Codagnone
 * @since Jul 26, 2010
 */
public class OpenEntityManagerRunnable implements Runnable {
    private Closure<?> runnable;
    
    /** constructor */
    public OpenEntityManagerRunnable(final EntityManagerFactory emf,
            final Runnable target) {
        runnable = new OpenEntityManagerClosure(emf, new Closure() {
            /** @see Closure#execute(Object) */
            public void execute(final Object t) {
                target.run();
            }
        });
    }
    /** @see Runnable#run() */
    public final void run() {
        runnable.execute(null);
    }
}

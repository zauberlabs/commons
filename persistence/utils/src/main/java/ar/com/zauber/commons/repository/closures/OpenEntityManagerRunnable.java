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
    private OpenEntityManagerClosure<Object> runnable;
    
    /** constructor */
    public OpenEntityManagerRunnable(final EntityManagerFactory emf,
            final Runnable target) {
        runnable = new OpenEntityManagerClosure<Object>(emf, new Closure<Object>() {
            /** @see Closure#execute(Object) */
            public void execute(final Object t) {
                target.run();
            }
        });
    }
    

    /** @see OpenEntityManagerClosure#setOpenTx(boolean) */
    public final void setOpenTx(final boolean openTx) {
        runnable.setOpenTx(openTx);
    }

    /** @see Runnable#run() */
    public final void run() {
        runnable.execute(null);
    }
}

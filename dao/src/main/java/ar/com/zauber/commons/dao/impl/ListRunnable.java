/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.dao.impl;

import java.util.List;

import org.apache.commons.lang.Validate;

/**
 * Runnable que corre otros {@link Runnable}s.
 *
 * @author Mariano Semelman
 * @since Dec 2, 2009
 */
public class ListRunnable implements Runnable {
    private final List<Runnable> runnables;

    /** Creates the ListRunnable. */
    public ListRunnable(final List<Runnable> runnables) {
        Validate.noNullElements(runnables);

        this.runnables = runnables;
    }

    /** @see Runnable#run() */
    public final void run() {
        for(final Runnable r : runnables) {
            r.run();
        }
    }
}

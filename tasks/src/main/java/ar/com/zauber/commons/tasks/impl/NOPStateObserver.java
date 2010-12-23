/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.tasks.impl;

import ar.com.zauber.commons.tasks.api.Milestone;
import ar.com.zauber.commons.tasks.api.TaskState;
import ar.com.zauber.commons.tasks.api.TaskStateObserver;

/**
 * Observer que funciona como NullObject
 * 
 * @author Mariano A Cortesi
 * @since Dec 14, 2010
 */
public class NOPStateObserver implements TaskStateObserver {

    @Override
    public void milestoneReached(final TaskState state,
            final Milestone milestone) {
        // nothing to do
    }

    @Override
    public void finishedOk(final TaskState state, final Milestone milestone) {
        // nothing to do
    }

    @Override
    public void finishedWithError(final TaskState state,
            final Milestone milestone, final Throwable e) {
        // nothing to do
    }

}

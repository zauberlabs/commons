/*
 * Copyright (c) 2011 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.tasks.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.zauber.commons.tasks.api.Milestone;
import ar.com.zauber.commons.tasks.api.TaskState;
import ar.com.zauber.commons.tasks.api.TaskStateObserver;

/**
 * {@link TaskStateObserver} implementation that logs the messages to
 * the a slf4j logger.
 * 
 * @author Mariano A Cortesi
 * @since Jan 5, 2011
 */
public class Slf4jTaskObserver implements TaskStateObserver {

    private final Logger logger = LoggerFactory.getLogger(Slf4jTaskObserver.class);
    
    @Override
    public final void milestoneReached(final TaskState taskState, final Milestone milestone) {
        if (logger.isInfoEnabled()) {
            logger.info("task:{} reached milestone:{} at:{}", 
                    new Object[] {taskState.getTaskName(), 
                                  milestone.getMilestoneName(), 
                                  milestone.getDate()});
            
        }
    }

    @Override
    public final void finishedOk(final TaskState taskState, final Milestone milestone) {
        if (logger.isInfoEnabled()) {
            logger.info("task:{} finished-ok:{} at:{}",
                    new Object[] {taskState.getTaskName(), 
                                  milestone.getMilestoneName(), 
                                  milestone.getDate()});
        }
    }

    @Override
    public final void finishedWithError(final TaskState taskState, final Milestone milestone, 
            final Throwable e) {
        if (logger.isInfoEnabled()) {
            logger.info("task:{} finished-error:{} at:{} error:{}({})", 
                    new Object[] {taskState.getTaskName(), 
                                  milestone.getMilestoneName(), 
                                  milestone.getDate(), 
                                  e.getClass(), 
                                  e.getMessage()});

        }
        if (logger.isErrorEnabled()) {
            logger.error("error-for: " + taskState.getTaskName(), e); 
        }
    }

}

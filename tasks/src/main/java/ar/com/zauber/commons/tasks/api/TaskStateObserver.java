/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.tasks.api;

/**
 * Modela a un observer para un {@link TaskState}
 * 
 * @author Mariano A Cortesi
 * @since Dec 14, 2010
 */
public interface TaskStateObserver {

    /** Evento que indica que se ha alcanzado un milestone */
    void milestoneReached(TaskState taskState, Milestone milestone);

    /** Evento que indica que ha finalizado la {@link Task} */
    void finishedOk(TaskState taskState, Milestone milestone);

    /** Evento que indica que ha finalizado la {@link Task} con errores */ 
    void finishedWithError(TaskState taskState, Milestone milestone, Throwable e);

}

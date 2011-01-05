/*
 * Copyright (c) 2011 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.tasks.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.tasks.api.Task;
import ar.com.zauber.commons.tasks.api.TaskDirector;
import ar.com.zauber.commons.tasks.api.TaskStateObserver;

/**
 * Base class for {@link TaskDirector}. Subclasses only need to implement the
 * {@link BaseTaskDirector#doLaunch(Task, TaskStateObserver)} method.
 * 
 * @author Mariano A Cortesi
 * @since Jan 5, 2011
 */
public abstract class BaseTaskDirector implements TaskDirector {

    private final Map<String, Task> tasksMap = new HashMap<String, Task>();
    private TaskStateObserver taskStateObserver = NOPStateObserver.INSTANCE;
    
    @Override
    public final void addTask(final Task task) throws IllegalArgumentException {
        Validate.notNull(task);
        if (tasksMap.containsKey(task.getName())) {
            throw new IllegalArgumentException("task '" + task.getName() + "' already defined");
        }
        this.tasksMap.put(task.getName(), task);

    }

    @Override
    public final void launch(final String taskName) throws IllegalArgumentException {
        Validate.notNull(taskName);
        Task task = this.tasksMap.get(taskName);
        if (task == null) {
            throw new IllegalArgumentException("task '" + taskName + "' does not exists");
        }
        this.doLaunch(task, taskStateObserver);
    }
    
    @Override
    public final void launchAll() {
        for (Task task : this.tasksMap.values()) {
            this.doLaunch(task, taskStateObserver);
        }
    }

    /**
     * Performs the actual task launch for a given {@link Task} using a given
     * {@link TaskStateObserver}.
     * <p>
     * Called when {@link BaseTaskDirector#launch(String)} is called.
     */
    protected abstract void doLaunch(final Task task, final TaskStateObserver taskStateObserver);

    @Override
    public final void setTaskStateObserver(final TaskStateObserver taskObserver) {
        if (taskObserver == null) {
            this.taskStateObserver = NOPStateObserver.INSTANCE;
        } else {
            this.taskStateObserver = taskObserver;
        }

    }

}

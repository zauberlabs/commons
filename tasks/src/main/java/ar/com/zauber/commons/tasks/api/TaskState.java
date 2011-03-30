/**
 * Copyright (c) 2005-2011 Zauber S.A. <http://www.zaubersoftware.com/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ar.com.zauber.commons.tasks.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ar.com.zauber.commons.date.DateProvider;
import ar.com.zauber.commons.date.impl.CurrentDateProvider;
import ar.com.zauber.commons.tasks.impl.NOPStateObserver;
import ar.com.zauber.commons.validate.Validate;

/**
 * Represents the state and history of a {@link Task}
 * 
 * @author Mariano A Cortesi
 * @since Dec 14, 2010
 */
public class TaskState {

    private final String taskName;

    private final List<Warning> warnings;
    private final List<Milestone> milestones;
    private final DateProvider dateProvider;
    private final TaskStateObserver observer;
    private Throwable error;
    private boolean finished = false;

    /** Creates the TaskState. */
    public TaskState(final String taskName) {
        this(taskName, NOPStateObserver.INSTANCE, new CurrentDateProvider());
    }

    /** Creates the TaskState. */
    public TaskState(final String taskName, final TaskStateObserver observer) {
        this(taskName, observer, new CurrentDateProvider());
    }

    /** Creates the TaskState. */
    public TaskState(final String taskName, final TaskStateObserver observer, final DateProvider dateProvider) {
        Validate.notBlank(taskName, observer, dateProvider);
        this.taskName = taskName;
        this.milestones = new ArrayList<Milestone>();
        this.warnings = new ArrayList<Warning>();
        this.observer = observer;
        this.dateProvider = dateProvider;
        this.addMilestone("started");
    }
    
    /** Reports the reach of a milestone in a {@link Task}  */
    public final void addMilestone(final String milestoneName) {
        if (this.finished) {
            throw new IllegalStateException("task is already finished");
        }
        Milestone milestone = new Milestone(dateProvider.getDate(), milestoneName);
        this.milestones.add(milestone);
        this.observer.milestoneReached(this, milestone);
    }
    
    /** Reports a warning in a {@link Task}  */
    public final void addWarning(final String type, final String description) {
        if (this.finished) {
            throw new IllegalStateException("task is already finished");
        }
        Warning warning = new Warning(dateProvider.getDate(), type, description);
        this.warnings.add(warning);
        this.observer.addWarning(this, warning);
    }

    /** Reports that the {@link Task} has finished without errors */
    public final void finishedOk() {
        this.finished = true;
        Milestone milestone = new Milestone(dateProvider.getDate(), "finished");
        this.milestones.add(milestone);
        this.observer.finishedOk(this, milestone);
    }

    /** Reports that the {@link Task} has finished with errors */
    public final void finishedWithError(final Throwable e) {
        this.finished = true;
        this.error = e;
        Milestone milestone = new Milestone(dateProvider.getDate(), "finished-with-error");
        this.milestones.add(milestone);
        this.observer.finishedWithError(this, milestone, e);        
    }

    /** @return <code>true</code> if no errors were reported  */
    public final boolean hasErrors() {
        return this.error != null;
    }

    /** @return <code>true</code> if the {@link Task} has finished  */
    public final boolean isFinished() {
        return finished;
    }
    
    public final String getTaskName() {
        return taskName;
    }
    
    public final List<Milestone> getMilestones() {
        return Collections.unmodifiableList(milestones);
    }

    @Override
    public final String toString() {
        return "TaskState[" + taskName + "]";
    }
    
}

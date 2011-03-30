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
package ar.com.zauber.commons.tasks.impl;

import java.util.Set;

import ar.com.zauber.commons.tasks.api.Task;
import ar.com.zauber.commons.tasks.api.TaskDirector;
import ar.com.zauber.commons.tasks.api.TaskState;
import ar.com.zauber.commons.tasks.api.TaskStateObserver;
import ar.com.zauber.commons.validate.Validate;

/**
 * Simple implementations of {@link TaskDirector}, that doesn't manage retries.
 * 
 * @author Pablo Martin Grigolatto
 * @since Dec 14, 2010
 */
public class DefaultTaskDirector extends BaseTaskDirector {

    /** Creates {@link DefaultTaskDirector} */
    public DefaultTaskDirector(final Set<Task> tasks) {
        Validate.notNull(tasks);
        for (Task task : tasks) {
            this.addTask(task);
        }
    }
    
    @Override
    protected void doLaunch(Task task, TaskStateObserver taskStateObserver) {
        TaskState taskState = new TaskState(task.getName(), taskStateObserver);
        task.launch(taskState);
        if (taskState.hasErrors()) {
            //que hacer cuando hay errores
        }
    }

}

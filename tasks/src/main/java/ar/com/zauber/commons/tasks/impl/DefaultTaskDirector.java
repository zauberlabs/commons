/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ar.com.zauber.commons.tasks.api.Task;
import ar.com.zauber.commons.tasks.api.TaskDirector;
import ar.com.zauber.commons.tasks.api.TaskState;
import ar.com.zauber.commons.tasks.api.TaskStateObserver;
import ar.com.zauber.commons.validate.Validate;

/**
 * Implementación de {@link TaskDirector}
 * 
 * @author Pablo Martin Grigolatto
 * @since Dec 14, 2010
 */
public class DefaultTaskDirector implements TaskDirector {

    private final Map<String, Task> tasksMap;
    private TaskStateObserver taskStateObserver = new NOPStateObserver();

    /** Creates {@link DefaultTaskDirector} */
    public DefaultTaskDirector(final Set<Task> tasks) {
        Validate.notNull(tasks);
        tasksMap = new HashMap<String, Task>();
        for (Task task : tasks) {
            tasksMap.put(task.getName(), task);
        }
    }
    
    public final void setTaskStateObserver(final TaskStateObserver taskStateObserver) {
        this.taskStateObserver = taskStateObserver;
    }
        
    @Override
    public final void launch(final String taskName) throws IllegalArgumentException {
        Validate.notBlank(taskName);
        Validate.isTrue(tasksMap.containsKey(taskName), "unknown task: " + taskName);
        TaskState taskState = new TaskState(taskName, taskStateObserver);
        tasksMap.get(taskName).launch(taskState);
        if (taskState.hasErrors()) {
            //que hacer cuando hay errores
        }
    }

    @Override
    public final void launchBlackListServiceReconfiguration() {
        this.launch("blacklist-reconfigure");
    }

    @Override
    public final void launchCountryResolverReconfiguration() {
        this.launch("ipmapper-reconfigure");
    }

    @Override
    public final void launchAdProviderSelectorReconfiguration() {
        this.launch("adproviderselector-reconfigure");
    }

}

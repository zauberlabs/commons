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
package ar.com.zauber.commons.tasks.api;

/**
 * Responsible of lauchings {@link Task}s and managing retries, task logging 
 * and reporting.
 * 
 * @author Pablo Martin Grigolatto
 * @since Dec 14, 2010
 */
public interface TaskDirector {

    /**
     * Adds a {@link Task} to the {@link TaskDirector}
     * 
     * @throws IllegalArgumentException If a task with the same name already exists
     * @see Task#getName()
     */
    void addTask(final Task task) throws IllegalArgumentException;
    
    /**
     * Launchs a {@link Task} previously known by the {@link TaskDirector}
     * 
     * @param taskName
     *            name of the task to be launched
     * @throws IllegalArgumentException
     *             If task name is not known by the {@link TaskDirector}
     * @see Task#getName()
     */
    void launch(final String taskName) throws IllegalArgumentException;
    
    /** Launches all configured {@link Task}s */
    void launchAll() ;
   
    /**
     * Configures the {@link TaskStateObserver} for every task launched within
     * the {@link TaskDirector}.
     * 
     * @param taskObserver observer to use, or <code>null</code> to set none
     */
    void setTaskStateObserver(final TaskStateObserver taskObserver);
}

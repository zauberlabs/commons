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

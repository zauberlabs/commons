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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.zauber.commons.tasks.api.Milestone;
import ar.com.zauber.commons.tasks.api.TaskState;
import ar.com.zauber.commons.tasks.api.TaskStateObserver;
import ar.com.zauber.commons.tasks.api.Warning;

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
                                  milestone.getTimestamp()});
            
        }
    }

    @Override
    public final void addWarning(final TaskState taskState, final Warning warning) {
        if (logger.isWarnEnabled()) {
            logger.warn("task:{} warning:{} at:{} description:{}", 
                    new Object[] {taskState.getTaskName(), 
                    warning.getWarningType(), 
                    warning.getTimestamp(),
                    warning.getDescription()
                    });
            
        }
    }

    @Override
    public final void finishedOk(final TaskState taskState, final Milestone milestone) {
        if (logger.isInfoEnabled()) {
            logger.info("task:{} finished-ok:{} at:{}",
                    new Object[] {taskState.getTaskName(), 
                                  milestone.getMilestoneName(), 
                                  milestone.getTimestamp()});
        }
    }

    @Override
    public final void finishedWithError(final TaskState taskState, final Milestone milestone, 
            final Throwable e) {
        if (logger.isInfoEnabled()) {
            logger.info("task:{} finished-error:{} at:{} error:{}({})", 
                    new Object[] {taskState.getTaskName(), 
                                  milestone.getMilestoneName(), 
                                  milestone.getTimestamp(), 
                                  e.getClass(), 
                                  e.getMessage()});

        }
        if (logger.isErrorEnabled()) {
            logger.error("error-for: " + taskState.getTaskName(), e); 
        }
    }

}

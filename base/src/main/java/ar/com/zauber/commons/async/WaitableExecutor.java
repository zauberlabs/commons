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
package ar.com.zauber.commons.async;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * Executor that has the ability to wait for the completion of all the tasks.
 * 
 * @author Juan F. Codagnone
 * @since Mar 12, 2011
 */
public interface WaitableExecutor extends Executor {
    
    /**
     * <p>
     * Blocks until all works are done. This means that there is no jobs running or in 
     * the internal queus, and all jobs has been executed.
     * </p>
     * <p>
     * For example, if a jobs is executing and it puts another job to execute (recursively)
     * then  {@link #awaitIdleness()} assures that all has been processed.
     * 
     * @throws InterruptedException
     */
    void awaitIdleness() throws InterruptedException;

    /**
     *  Like {@link #awaitIdleness()} but with a timeout.
     *  
     *   @return <tt>true</tt> if this executor terminated and <tt>false</tt> if
     *         the timeout elapsed before termination
     */
    boolean awaitIdleness(final long timeout, final TimeUnit unit) 
             throws InterruptedException;
}

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
package ar.com.zauber.commons.dao;

import org.apache.commons.lang.Validate;

/**
 * Enables to run a {@link ClosureProcessor} as a {@link Runnable}. 
 * 
 * @author Juan F. Codagnone
 * @since Jun 19, 2009
 * @param <T> Closure's entity class
 */
public class ClosureProcessorRunnable<T> implements Runnable {
    private final ClosureProcessor<T> closureProcessor;
    private final Closure<T> closure;
 

    /**
     * @param closureProcessor processor
     * @param closure closure that is the argument of the processor
     */
    public ClosureProcessorRunnable(final ClosureProcessor<T> closureProcessor,
            final Closure<T> closure) {
        Validate.notNull(closureProcessor);
        Validate.notNull(closure);
        
        this.closureProcessor = closureProcessor;
        this.closure = closure;
    }
    
    /** @see Runnable#run() */
    public final void run() {
        closureProcessor.process(closure);
    }
}

/**
 * Copyright (c) 2005-2012 Zauber S.A. <http://www.zaubersoftware.com/>
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
package ar.com.zauber.commons.dao.closure;

import java.util.concurrent.Executor;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Closure;

/**
 * Closure que delega la ejecución en un {@link Executor}; posiblemente
 * en otro thread. 
 * 
 * @author Juan F. Codagnone
 * @since May 14, 2010
 * @param <T> tipo del closure
 */
public class ExecutorClosure<T> implements Closure<T> {
    private final Closure<T> target;
    private final Executor executor;

    /** Creates the ExecutorClosure. */
    public ExecutorClosure(final Closure<T> target, final Executor executor) {
        Validate.notNull(target);
        
        this.target = target;
        this.executor = executor;
    }
    
    /** @see Closure#execute(Object) */
    public final void execute(final T t) {
        executor.execute(new Runnable() {
            public void run() {
                target.execute(t);
            }
        });
    };
}

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
package ar.com.zauber.commons.dao.closure;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.zauber.commons.dao.Closure;

/**
 * Broadcastea el execute a muchos otros closures
 * 
 * @author Juan F. Codagnone
 * @since Jun 23, 2009
 * @param <T> tipo de los {@link Closure}s.
 */
public class ComposeClosure<T> implements Closure<T> {
    private final Iterable<Closure<T>> closures;
    private final Closure<Throwable> errorClosure;
    
    /** Creates the ComposeClosure. */
    public ComposeClosure(final Iterable<Closure<T>> closures) {
        this(closures, new Closure<Throwable>() {
            private Logger logger = LoggerFactory.getLogger(ComposeClosure.class);
            public void execute(final Throwable e) {
                logger.error("executing closure...ignoring", e);
            }
        });
    }
    
    /** Creates the ComposeClosure. */
    public ComposeClosure(final Iterable<Closure<T>> closures, 
            final Closure<Throwable> errorClosure) {
        Validate.notNull(closures);
        Validate.notNull(errorClosure);
        
        this.closures = closures;
        this.errorClosure = errorClosure;
    }
    
    /** @see Closure#execute(Object) */
    public final void execute(final T t) {
        for(final Closure<T> closure : closures) {
            try {
                closure.execute(t);
            } catch(final Throwable e) {
                errorClosure.execute(e);
            }
        }
    }

    public final Iterable<Closure<T>> getClosures() {
        return closures;
    }

    public final Closure<Throwable> getErrorClosure() {
        return errorClosure;
    }
}

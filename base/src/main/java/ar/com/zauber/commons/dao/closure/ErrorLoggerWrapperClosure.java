/**
 * Copyright (c) 2005-2015 Zauber S.A. <http://flowics.com/>
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



import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.zauber.commons.dao.Closure;

/**
 * Loguea las exepciones. util para que sea el closure de mas afuera de una
 * threadpool.
 * 
 * @author Juan F. Codagnone
 * @since Jan 25, 2010
 * @param <T> Type
 */
public class ErrorLoggerWrapperClosure<T> implements Closure<T> {
    private final Closure<T> target;
    private final Closure<Throwable> errorClosure;
    
    /** Creates the WrapperClosure. */
    public ErrorLoggerWrapperClosure(final Closure<T> target) {
        this(target, new Closure<Throwable>() {
            private final Logger logger = LoggerFactory.getLogger(
                    ErrorLoggerWrapperClosure.class);
            public void execute(final Throwable e) {
                logger.error("processing closure", e);
                throw new UnhandledException(e);    
            }
        });
    }
    /** Creates the WrapperClosure. */
    public ErrorLoggerWrapperClosure(final Closure<T> target, 
            final Closure<Throwable> errorClosure) {
        Validate.notNull(target);
        Validate.notNull(errorClosure);
        
        this.target = target;
        this.errorClosure = errorClosure;
    }
    
    /** @see Closure#execute(Object) */
    public final void execute(final T t) {
        try {
            target.execute(t);
        } catch(final Throwable e) {
            errorClosure.execute(e);
        }
    }
}

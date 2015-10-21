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
package ar.com.zauber.commons.dao.closure.wrapper;

import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.commons.dao.closure.ClosureWrapperFactory;
import ar.com.zauber.commons.dao.closure.ErrorLoggerWrapperClosure;

/**
 * Crea un {@link ErrorLoggerWrapperClosure}
 * 
 * @author Juan F. Codagnone
 * @since Oct 29, 2010
 */
public class ErrorLoggerClosureWrapperFactory<T> implements ClosureWrapperFactory<T> {
    private final Closure<Throwable> errorClosure;
    
    /** Creates the ErrorLoggerClosureWrapperFactory. */
    public ErrorLoggerClosureWrapperFactory() {
        this(null);
    }
    
    /** Creates the ErrorLoggerClosureWrapperFactory. */
    public ErrorLoggerClosureWrapperFactory(final Closure<Throwable> errorClosure) {
        this.errorClosure = errorClosure;
    }
    
    @Override
    public Closure<T> decorate(final Closure<T> target) {
        return new ErrorLoggerWrapperClosure<T>(target, errorClosure);
    }
}

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

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.commons.dao.Predicate;

/**
 * Clausara que delega en otra, si un filtro da positivo
 * 
 * 
 * @author Juan F. Codagnone
 * @since Jun 27, 2009
 * @param <T> Entidad para filtrar
 */
public class FilteredClosure<T> implements Closure<T> {
    private final Predicate<T> predicate;
    private final Closure<T> trueClosure;
    private final Closure<T> falseClosure;

    /** Creates the FilteredClosure. */
    public FilteredClosure(final Predicate<T> predicate,
            final Closure<T> trueClosure) {
        this(predicate, trueClosure, (Closure<T>) Closures.nullClosure());
    }
            
    /** Creates the FilteredClosure. */
    public FilteredClosure(final Predicate<T> predicate,
            final Closure<T> trueClosure,
            final Closure<T> falseClosure) {
        Validate.notNull(predicate);
        Validate.notNull(trueClosure);
        Validate.notNull(falseClosure);
        
        this.predicate = predicate;
        this.trueClosure = trueClosure;
        this.falseClosure = falseClosure;
    }
    
    /** @see Closure#execute(Object) */
    public final void execute(final T t) {
        if(predicate.evaluate(t)) {
            trueClosure.execute(t);
        } else {
            falseClosure.execute(t);
        }
    }
}

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
package ar.com.zauber.commons.dao.closure.processors;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.commons.dao.ClosureProcessor;

/**
 * {@link ClosureProcessor} que obtiene todos los elementos a procesar de un
 * iterable. Es muy util para pruebas. 
 * 
 * @author Matías G. Tito
 * @since Sep 29, 2009
 * @param <T> Tipo de entidad
 */
public class IterableClosureProcesor<T> implements ClosureProcessor<T> {
    private final Iterable<T> source;

    /** Creates the IterableClosureProcesor. */
    public IterableClosureProcesor(final Iterable<T> source) {
        Validate.notNull(source);
        
        this.source = source;
    }
    
    /** @see ClosureProcessor#process(Closure) */
    public final void process(final Closure<T> closure) {
        Validate.notNull(closure);
        for(final T entry : source) {
            closure.execute(entry);
        }
    }
}

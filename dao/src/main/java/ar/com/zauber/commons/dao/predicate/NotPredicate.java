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
package ar.com.zauber.commons.dao.predicate;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Predicate;

/**
 * {@link Predicate} que delega a otro y niega su respuesta.
 * 
 * @author Juan F. Codagnone
 * @since Jun 27, 2009
 * @param <T> entity type
 */
public class NotPredicate<T> implements Predicate<T> {
    private final Predicate<T> predicate;

    /** Creates the NotPredicate. */
    public NotPredicate(final Predicate<T> predicate) {
        Validate.notNull(predicate);
        
        this.predicate = predicate;
    }
    
    /** @see Predicate#evaluate(java.lang.Object) */
    public final boolean evaluate(final T value) {
        return !predicate.evaluate(value);
    }
}

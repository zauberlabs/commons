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
package ar.com.zauber.commons.dao.closure;

import java.util.Map.Entry;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.commons.dao.Predicate;

/**
 * @see SwitchClosure.
 * 
 * @author Juan F. Codagnone
 * @since Jan 22, 2010
 * @param <T> type
 */
public class PredicateClosureEntry<T> implements Entry<Predicate<T>, Closure<T>> {
    private final Predicate<T> predicate;
    private final Closure<T> closure;

    /** Creates the PredicateClosureEntry. */
    public PredicateClosureEntry(final Predicate<T> predicate, 
            final Closure<T> closure) {
        Validate.notNull(predicate);
        Validate.notNull(closure);
        
        this.predicate = predicate;
        this.closure = closure;
    }
    
    /** @see Entry#getKey() */
    public final Predicate<T> getKey() {
        return predicate;
    }

    /** @see Entry#getValue() */
    public final Closure<T> getValue() {
        return closure;
    }

    /** @see Entry#setValue(Object) */
    public final Closure<T> setValue(final Closure<T> value) {
        throw new UnsupportedOperationException();
    }

}

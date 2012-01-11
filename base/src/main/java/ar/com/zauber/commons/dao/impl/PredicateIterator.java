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
package ar.com.zauber.commons.dao.impl;

import java.util.Iterator;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Predicate;

/**
 * Iterador que filtra el contenido de otro iterador `on the fly'.
 * 
 * @author Juan F. Codagnone
 * @since Jun 16, 2009
 * @param <T> type
 */
public class PredicateIterator<T> implements Iterator<T> {
    private final Iterator<T> target;
    private final Predicate<T> predicate;
    private T current;

    /**
     * Creates the PredicateIterator.
     *
     * @param target     iterador posta
     * @param predicate  el predicado utilizado para filtrar
     */
    public PredicateIterator(final Iterator<T> target,
            final Predicate<T> predicate) {
        Validate.notNull(target);
        Validate.notNull(predicate);
        
        this.target = target;
        this.predicate = predicate;
        
        next();
    }
    
    /** @see Iterator#hasNext() */
    public final boolean hasNext() {
        return current != null;
    }

    /** @see Iterator#next() */
    public final T next() {
        T ret = current;
        current = null;
        while(target.hasNext()) {
            current = target.next();
            if(predicate.evaluate(current)) {
                break;
            } else {
                current = null;
            }
        }
        
        return ret;
    }

    /** @see Iterator#remove() */
    public final void remove() {
        throw new UnsupportedOperationException("remove() not supported");
    }

}

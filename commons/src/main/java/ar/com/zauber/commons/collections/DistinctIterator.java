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
package ar.com.zauber.commons.collections;

import java.util.Comparator;
import java.util.Iterator;

import org.apache.commons.lang.Validate;

/**
 * Iterador que mientras itera elimina duplicados que acaba de ver.
 * ["hola", "hola", "chau", "chau"] retorna ["hola", "chau"], pero 
 * ["hola", "chau", "hola"] retorna ["hola", "chau", "hola"].  
 * 
 * @author Juan Almeyda
 * @since Sep 7, 2009
 * @param <T> type of the iterator
 */
public class DistinctIterator<T> implements Iterator<T> {
    private final Iterator<T> target;
    private final Comparator<T> comparator;
    private T buffer;

    /** Creates the DistinctIterator. */
    public DistinctIterator(final Iterator<T> target, 
            final Comparator<T> comparator) {
        Validate.notNull(target);
        Validate.notNull(comparator);
        
        this.target = target;
        this.comparator = comparator;
        
        readNext();
    }
    
    
    /** @see Iterator#hasNext() */
    public final boolean hasNext() {
        return buffer != null;
    }

    /** @see Iterator#next() */
    public final T next() {
        if(buffer == null) {
            throw new UnsupportedOperationException("no more elements");
        }
        final T ret = buffer;
        readNext();
        return ret;
    }

    /** @see Iterator#remove() */
    public final void remove() {
        throw new UnsupportedOperationException("not implemented");
    }


    /** avanzo al sigueinte elemento distinto al anterior*/
    private void readNext() {
        T aux = buffer;
        if(target.hasNext()) {
            if(buffer == null) {
                buffer = target.next();
            } else {
                while(target.hasNext()) {
                    T n = target.next();
                    if(comparator.compare(n, buffer) != 0) {
                        buffer = n;
                        break;
                    }
                }
                if(!target.hasNext() && buffer == aux) {
                    buffer = null;
                }
            }
        } else {
            buffer = null;
        }
    }

}

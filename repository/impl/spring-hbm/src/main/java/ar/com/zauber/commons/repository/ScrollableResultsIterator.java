/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
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
package ar.com.zauber.commons.repository;

import java.util.Iterator;

import org.apache.commons.lang.Validate;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;

/**
 * Iterator que engloba los ScrollableResults de Hibernate para trabajar 
 * genéricamente con ObservationWorkingDayHoursProvider. Se usa de acuerdo
 * al algoritmo de este Provider, es decir que el hasNext avanza el scroll
 * y el next devuelve el elemento cargado.
 * 
 * 
 * @author Cecilia Hagge
 * @since Jun 10, 2009
 * @param <T> tipo del scroll
 */
public class ScrollableResultsIterator<T> implements Iterator<T> {
    
    private final ScrollableResults results;
    private final Session session;
    private T buffer;
    
    /** Creates the ScrollableResultsIterator */
    public ScrollableResultsIterator(final ScrollableResults results,
            final Session session) {
        Validate.notNull(results);
        Validate.notNull(session);
        
        this.results = results;
        this.session = session;
        
        readNextFromScrollable();
    }
    
    /** @see Iterator#hasNext() */
    public final boolean hasNext() {
        return buffer != null;
    }

    /** @see Iterator#next() */
    public final T next() {
        if(buffer == null) {
            throw new UnsupportedOperationException("no more rows");
        }
        T ret = buffer; 
        readNextFromScrollable();
        return ret;
    }

    /** lee el siguiente objeto del scroll */
    @SuppressWarnings("unchecked")
    private void readNextFromScrollable() {
        boolean hasNext = results.next();
        if(hasNext) {
            buffer = (T) results.get(0);
            this.session.evict(buffer);
        } else {
            buffer = null;
        }
    }

    /** @see Iterator#remove() */
    public final void remove() {
        throw new UnsupportedOperationException("not implemented");
    }
    
}

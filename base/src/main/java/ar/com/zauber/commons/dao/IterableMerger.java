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
package ar.com.zauber.commons.dao;

import java.util.Comparator;
import java.util.Iterator;

import org.apache.commons.lang.Validate;

/**
 * Realiza el merge entre 2 {@link Iterable}s, aplicando un {@link Closure} a
 * cada elemento. Da prioridad a los elementos del primer {@link Iterable} que
 * recibe.
 * 
 * @author Pablo Martin Grigolatto
 * @since Jul 2, 2010
 * @param <T> el tipo de los iterables
 */
public class IterableMerger<T> {
    private final Comparator<T> comparator;
    private final Closure<T> target;

    private Iterator<T> left;
    private Iterator<T> right;
    private T l = null;
    private T r = null;

    /** Creates the MergeIterablesClosure. */
    public IterableMerger(
            final Comparator<T> comparator, final Closure<T> target) {
        
        Validate.notNull(comparator);
        Validate.notNull(target);
        this.comparator = comparator;
        this.target = target;
    }

    /** Realiza el merge aplicando el {@link Closure} */
    public final void merge(final Iterable<T> iterableLeft,
            final Iterable<T> iterableRight) {

        Validate.notNull(iterableLeft);
        Validate.notNull(iterableRight);
        left = iterableLeft.iterator();
        right = iterableRight.iterator();

        forwardLeft();
        forwardRight();

        while (l != null && r != null) {
            int diff = comparator.compare(l, r);

            if (diff <= 0) {
                target.execute(l);
                forwardLeft();
            } else {
                target.execute(r);
                forwardRight();
            }
        }

        while (l != null) {
            target.execute(l);
            forwardLeft();
        }

        while (r != null) {
            target.execute(r);
            forwardRight();
        }
    }

    /** avanza el puntero de la izquierda */
    private void forwardLeft() {
        l = null;
        if (left.hasNext()) {
            l = left.next();
        }
    }

    /** avanza el puntero de la derecha */
    private void forwardRight() {
        r = null;
        if (right.hasNext()) {
            r = right.next();
        }
    }
}
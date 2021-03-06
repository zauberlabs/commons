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
package ar.com.zauber.commons.dao.closure.processors;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.commons.dao.ClosureProcessor;
import ar.com.zauber.commons.dao.closure.processors.MergeResult.Operation;

/**
 * One-way merge de dos iteratadores. En un iterador tiene lo que deberia ser el
 * resultado. En el otro tiene los datos actuales. El procesador va diciendo
 * que cosas hay que borrar y que cosas hay que agregar para llegar a ese 
 * estado final.
 * 
 * @author Juan F. Codagnone
 * @since Jun 18, 2009
 * @param <T> clase de entidad a mergear
 */
public class OneWayMergeClosureProcessor<T> 
       implements ClosureProcessor<MergeResult<T>> {
    private final Iterable<T> currentStateIterable;
    private final Iterable<T> idealStateIterable;
    private final Comparator<T> comparator;
    
    public OneWayMergeClosureProcessor(final Iterable<T> currentStateIterable,
                                       final Iterable<T> idealStateIterable) {
        this(currentStateIterable, idealStateIterable, (a, b)-> ((Comparable)a).compareTo(b));
    }
            
    /** 
     * @param currentStateIterable da un iterador del estado actual. el iterador 
     * tiene que estar ordenado lexicograficamente.
     * @param idealStateIterable el iterador  tiene que estar ordenado 
     * lexicograficamente.
     */
    public OneWayMergeClosureProcessor(final Iterable<T> currentStateIterable,
            final Iterable<T> idealStateIterable, 
            final Comparator<T> comparator) {
        Validate.notNull(currentStateIterable);
        Validate.notNull(idealStateIterable);
        Validate.notNull(comparator);
        
        this.currentStateIterable = currentStateIterable;
        this.idealStateIterable = idealStateIterable;
        this.comparator = comparator;
    }

    /** @see ClosureProcessor#process(Closure) */
    public final void process(final Closure<MergeResult<T>> closure) {
        new RosterSync().process(closure, null);
    }
    
    /** @see ClosureProcessor#process(Closure) */
    public final void process(final Closure<MergeResult<T>> closure,
            final Consumer<List<T>> keepClosure) {
        new RosterSync().process(closure, keepClosure);
    }
    
    /**
     * @author Juan F. Codagnone
     * @since Jun 19, 2009
     */
    class RosterSync {
        private final Iterator<T> left  = idealStateIterable.iterator();
        private final Iterator<T> right =  currentStateIterable.iterator();
        private T l = null;
        private T r = null;
        
        /** se encarga de sincronizar las listas 
         * @param keepClosure */
        public final void process(final Closure<MergeResult<T>> closure, final Consumer<List<T>> keepClosure) {
            forwardLeft();
            forwardRight();
            
            while(l != null && r != null) {
                int i = comparator.compare(l, r);
                if(i < 0) {
                    notifyAdd(closure);
                    forwardLeft();
                } else if(i == 0) {
                    notifyKeep(closure, keepClosure);
                    forwardLeft();
                    forwardRight();
                } else if(i > 0) {
                    notifyRemove(closure);
                    forwardRight();
                }
            }
            
            while(l != null) {
                notifyAdd(closure);
                forwardLeft();
            }
            
            while(r != null) {
                notifyRemove(closure);
                forwardRight();
            }
        }

        /** notifica que se debe remover un contexto */
        protected void notifyRemove(final Closure<MergeResult<T>> closure) {
            closure.execute(new InmutableMergeResult<T>(Operation.REMOVE, r));
        }

        /** notifica que se debe agregar un contexto */
        protected void notifyAdd(final Closure<MergeResult<T>> closure) {
            closure.execute(new InmutableMergeResult<T>(Operation.ADD, l));
        }
        
        /** notifica que no se debe hacer nada con un contexto */
        protected void notifyKeep(final Closure<MergeResult<T>> closure,
                                  final Consumer<List<T>> keepClosure) {
            closure.execute(new InmutableMergeResult<T>(Operation.KEEP, l));
            if(keepClosure != null) {
                final List<T> ret = new ArrayList<>(2);
                ret.add(r);
                ret.add(l);
                keepClosure.accept(ret);
            }
        }

        /** avanza el puntero del iterador de la izquierda */
        private void forwardLeft() {
            l = null;
            if(left.hasNext()) {
                l = left.next();
            }
        }
        
        /** avanza el puntero del iterador de la derecha */
        private void forwardRight() {
            r = null;
            if(right.hasNext()) {
                r = right.next();
            }
        }
    }
}

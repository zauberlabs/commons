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

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;


/**
 * Base implementation for {@link Set} fowards all the messages to a target 
 * {@link Set}, but let you hooks on modification events (know when a item
 * is added or removed from the list) 
 *  
 * @author Juan F. Codagnone
 * @since Jan 29, 2009
 * @param <T> entity type
 */
public abstract class OnModificationProxyCollection<T> implements Collection<T>  {
    

    /** @see Set#addAll(Collection) */
    public final boolean addAll(final Collection<? extends T> c) {
        boolean ret = false;
        for(final T t : c) {
            ret |= this.add(t);
        }
        return ret;
    }


    /** @see Set#contains(Object) */
    public final boolean contains(final Object o) {
        return getTarget().contains(o);
    }

    /** @see Set#containsAll(Collection) */
    public final  boolean containsAll(final Collection<?> c) {
        return getTarget().containsAll(c);
    }

    /** @see Set#equals(java.lang.Object) */
    @Override
    public final boolean equals(final Object o) {
        boolean ret;
        
        if(o == this) {
            ret = true;
        } else if(o instanceof OnModificationProxyCollection<?>) {
            ret = getTarget().equals(
                    ((OnModificationProxyCollection<?>)o).getTarget());
        } else {
            ret = getTarget().equals(o);
        }
        
        return ret; 
    }

    /** @see Set#hashCode() */
    @Override
    public final int hashCode() {
        return getTarget().hashCode();
    }

    /** @see Set#isEmpty() */
    public final boolean isEmpty() {
        return getTarget().isEmpty();
    }

    /** @see Set#iterator() */
    public final Iterator<T> iterator() {
        return new OnmodificationProxyIterator(getTarget().iterator());
    }
    
    /** {@link Iterator} that fires modification events */
    class OnmodificationProxyIterator implements Iterator<T> {
        private final Iterator<T> iterator;
        private T lastNext;

        /** Constructor */
        public OnmodificationProxyIterator(final Iterator<T> iterator) {
            this.iterator = iterator;
        }

        /** @see Iterator#hasNext() */
        public boolean hasNext() {
            return iterator.hasNext();
        }

        /** @see Iterator#next() */
        public T next() {
            lastNext = iterator.next(); 
            return lastNext;
        }

        /** @see Iterator#remove() */
        public void remove() {
            onRemovePre(lastNext);
            iterator.remove();
            onRemovePost(lastNext);
        }
    }

    /** @see Set#removeAll(java.util.Collection) */
    public final boolean removeAll(final Collection<?> c) {
        boolean ret = false;
        final Collection<T> target = getTarget();
        for(final Object t : c) {
            ret |= target.remove(t);
        }
        return ret;
    }

    /** @see Set#size() */
    public final int size() {
        return getTarget().size();
    }

    /** @see Set#toArray() */
    public final Object[] toArray() {
        return getTarget().toArray();
    }

    /** @see Set#toArray(T[]) */
    public final <T> T[] toArray(final T[] a) {
        return getTarget().toArray(a);
    }
    
    /** @see Set#add(Object) */
    public final boolean add(final T e) {
        final Collection<T> target = getTarget();
        if(!target.contains(e)) {
            onAddPre(e);
        }
        boolean ret = target.add(e);
        onAddPost(e);
        return ret;
    }
    
    /** @see Set#clear() */
    public final void clear() {
        for(final Iterator<T> iterator = iterator(); iterator.hasNext();) {
            iterator.next();
            iterator.remove();
        }
    }
    
    /** @see Set#retainAll(Collection) */
    public final boolean retainAll(final Collection<?> c) {
        final Collection<T> target = getTarget();
        for(final T e : target) {
            if(!c.contains(e)) {
                remove(e);
            }
        }
        return target.retainAll(c);
    }

    /** @see Set#remove(Object) */
    public final boolean remove(final Object o) {
        final Collection<T> target = getTarget();
        if(target.contains(o)) {
            onRemovePre((T)o);
        }
        boolean ret = target.remove(o);
        onRemovePost((T)o);
        return ret;
    }

    /** @see Object#toString() */
    @Override
    public final String toString() {
        return getTarget().toString();
    }
    
    // template methods
    
    /** called on remove before*/
    protected void onRemovePre(final T o) {
        // override
    }
    
    /** called on remove after*/
    protected  void onRemovePost(final T o) {
        // override
    }
    
    /** called on add before*/
    protected void onAddPre(final T e) {
        // override
    }
    
    /** called on add after*/
    protected void onAddPost(final T e) {
        // override
    }

    /** gets target set */
    protected abstract Collection<T> getTarget();
}
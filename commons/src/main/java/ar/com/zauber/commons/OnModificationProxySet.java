package ar.com.zauber.commons;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.Validate;

/**
 * Base implementation for {@link Set} fowards all the messages to a target 
 * {@link Set}, but let you hooks on modification events (know when a item
 * is added or removed from the list) 
 *  
 * @author Juan F. Codagnone
 * @since Jan 29, 2009
 * @param <T> entity type
 */
public abstract class OnModificationProxySet<T> implements Set<T>  {
    

    /** @see Set#addAll(Collection) */
    public final boolean addAll(final Collection<? extends T> c) {
        boolean ret = false;
        final Set<T> target = getTargetSet();
        for(final T t : c) {
            ret |= target.add(t);
        }
        return ret;
    }


    /** @see Set#contains(Object) */
    public final boolean contains(final Object o) {
        return getTargetSet().contains(o);
    }

    /** @see Set#containsAll(Collection) */
    public final  boolean containsAll(final Collection<?> c) {
        return getTargetSet().containsAll(c);
    }

    /** @see Set#equals(java.lang.Object) */
    @Override
    public final boolean equals(final Object o) {
        return getTargetSet().equals(o);
    }

    /** @see Set#hashCode() */
    @Override
    public final int hashCode() {
        return getTargetSet().hashCode();
    }

    /** @see Set#isEmpty() */
    public final boolean isEmpty() {
        return getTargetSet().isEmpty();
    }

    /** @see Set#iterator() */
    public final Iterator<T> iterator() {
        return getTargetSet().iterator();
    }

    /** @see Set#removeAll(java.util.Collection) */
    public final boolean removeAll(final Collection<?> c) {
        boolean ret = false;
        final Set<T> target = getTargetSet();
        for(final Object t : c) {
            ret |= target.remove(t);
        }
        return ret;
    }

    /** @see Set#size() */
    public final int size() {
        return getTargetSet().size();
    }

    /** @see Set#toArray() */
    public final Object[] toArray() {
        return getTargetSet().toArray();
    }

    /** @see Set#toArray(T[]) */
    public final <T> T[] toArray(final T[] a) {
        return getTargetSet().toArray(a);
    }
    
    /** @see Set#add(Object) */
    public final boolean add(final T e) {
        final Set<T> target = getTargetSet();
        if(!target.contains(e)) {
            onAdd(e);
        }
        return target.add(e);
    }
    

    /** @see Set#clear() */
    public final void clear() {
        final Set<T> target = getTargetSet();
        for(final T e : target) {
            remove(e);
        }
    }
    
    /** @see Set#retainAll(Collection) */
    public final boolean retainAll(final Collection<?> c) {
        final Set c1 = new HashSet(c);
        final Set<T> target = getTargetSet();
        for(final T e : target) {
            if(!c1.contains(e)) {
                remove(e);
            }
        }
        return target.retainAll(c);
    }

    /** @see Set#remove(Object) */
    public final boolean remove(final Object o) {
        final Set<T> target = getTargetSet();
        if(target.contains(o)) {
            onRemove((T)o);
        }
        return target.remove(o);
    }

    /** @see java.lang.Object#toString() */
    @Override
    public String toString() {
        return getTargetSet().toString();
    }
    
    /** called on remove */
    protected  abstract void onRemove(T o);
    
    /** called on add */
    protected abstract void onAdd(T e);

    /** gets target set */
    protected abstract Set<T> getTargetSet();
    
    
}
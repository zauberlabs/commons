package ar.com.zauber.commons;

import java.util.Collection;
import java.util.Collections;
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
    private final Set<T> target;
    private final Set<T> set;

    /** Creates the ModificableSet. */
    public OnModificationProxySet(final Set<T> target) {
        Validate.notNull(target);
        this.target = Collections.unmodifiableSet(target);
        this.set = target;
    }
    

    /** @see Set#addAll(Collection) */
    public final boolean addAll(final Collection<? extends T> c) {
        boolean ret = false;
        for(final T t : c) {
            ret |= target.add(t);
        }
        return ret;
    }


    /** @see Set#contains(Object) */
    public final boolean contains(final Object o) {
        return target.contains(o);
    }

    /** @see Set#containsAll(Collection) */
    public final  boolean containsAll(final Collection<?> c) {
        return target.containsAll(c);
    }

    /** @see Set#equals(java.lang.Object) */
    @Override
    public final boolean equals(final Object o) {
        return target.equals(o);
    }

    /** @see Set#hashCode() */
    @Override
    public final int hashCode() {
        return target.hashCode();
    }

    /** @see Set#isEmpty() */
    public final boolean isEmpty() {
        return target.isEmpty();
    }

    /** @see Set#iterator() */
    public final Iterator<T> iterator() {
        return target.iterator();
    }

    /** @see Set#removeAll(java.util.Collection) */
    public final boolean removeAll(final Collection<?> c) {
        boolean ret = false;
        for(final Object t : c) {
            ret |= target.remove(t);
        }
        return ret;
    }

    /** @see Set#size() */
    public final int size() {
        return target.size();
    }

    /** @see Set#toArray() */
    public final Object[] toArray() {
        return target.toArray();
    }

    /** @see Set#toArray(T[]) */
    public final <T> T[] toArray(final T[] a) {
        return target.toArray(a);
    }
    
    /** @see Set#add(Object) */
    public final boolean add(final T e) {
        if(!set.contains(e)) {
            onAdd(e);
        }
        return set.add(e);
    }
    

    /** @see Set#clear() */
    public final void clear() {
        for(final T e : set) {
            remove(e);
        }
    }
    
    /** @see Set#retainAll(Collection) */
    public final boolean retainAll(final Collection<?> c) {
        final Set c1 = new HashSet(c); 
        for(final T e : set) {
            if(!c1.contains(e)) {
                remove(e);
            }
        }
        return target.retainAll(c);
    }

    /** @see Set#remove(Object) */
    public final boolean remove(final Object o) {
        if(set.contains(o)) {
            onRemove((T)o);
        }
        return set.remove(o);
    }

    /** called on remove */
    protected  abstract void onRemove(T o);
    
    /** called on add */
    protected abstract void onAdd(T e);

}
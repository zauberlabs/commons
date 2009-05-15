/*
 * Copyright (c) 2009 Gire S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.Validate;

/**
 * A List that can be configured with a Comparator
 * at any moment and its elements are re-sorted
 * after that.
 *
 * @param <T> The kind of objects that you'll save within the collection  
 * @author Christian Nardi
 * @since May 15, 2009
 */
public class DynamicSortedCollection<T> implements Collection<T> {
    private final List<T> list = new ArrayList<T>();
    private Comparator<? super T> comparator;

    /**
     * Creates the DynamicSortedList.
     *
     * @param comparator init comparator to use
     * (it can be changed at any moment)
     */
    public DynamicSortedCollection(final Comparator<? super T> comparator) {
        Validate.notNull(comparator);
        this.comparator = comparator;
    }
    /** @see java.util.List#add(java.lang.Object) 
     * This operation is expensive, so unnecessary calls should be avoided
     * */
    public final boolean add(final T e) {
        final boolean ret = list.add(e);
        Collections.sort(list, comparator);
        return ret;
    }
    /** @see java.util.Collection#addAll(java.util.Collection) 
     * This operation is expensive, so unnecessary calls should be avoided
     * */
    public final boolean addAll(final Collection<? extends T> c) {
        final boolean ret = list.addAll(c);
        Collections.sort(list, comparator);
        return ret;
    }
    /** @see java.util.Collection#clear() */
    public final void clear() {
        list.clear();
    }
    /** @see java.util.Collection#contains(java.lang.Object) */
    public final boolean contains(final Object o) {
        return list.contains(o);
    }
    /** @see java.util.Collection#containsAll(java.util.Collection) */
    public final boolean containsAll(final Collection<?> c) {
        return list.containsAll(c);
    }
    /** @see java.util.Collection#isEmpty() */
    public final boolean isEmpty() {
        return list.isEmpty();
    }
    /** @see java.util.Collection#iterator() */
    public final Iterator<T> iterator() {
        return list.iterator();
    }
    /** @see java.util.Collection#remove(java.lang.Object) */
    public final boolean remove(final Object o) {
        //it doesnt need to be re sorted
        return list.remove(o);
    }
    /** @see java.util.Collection#removeAll(java.util.Collection) */
    public final boolean removeAll(final Collection<?> c) {
      //it doesnt need to be re sorted
        return list.removeAll(c);
    }
    /** @see java.util.Collection#retainAll(java.util.Collection) */
    public final boolean retainAll(final Collection<?> c) {
        return list.retainAll(c);
    }
    /** @see java.util.Collection#size() */
    public final int size() {
        return list.size();
    }
    /** @see java.util.Collection#toArray() */
    public final Object[] toArray() {
        return list.toArray();
    }
    /** @see java.util.Collection#toArray(T[]) */
    public final <T> T[] toArray(final T[] a) {
        return list.toArray(a);
    }

    /**
     * @param comparator the comparator used to re sorting the collection.
     * This operation is expensive, so unnecessary calls should be avoided 
     */
    public final void setComparator(final Comparator<? super T> comparator) {
        this.comparator = comparator;
        Collections.sort(list, comparator);
    }
}

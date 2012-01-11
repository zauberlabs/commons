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
package ar.com.zauber.commons.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
public class DynamicSortedCollection<T> implements List<T> {
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
    
    /** @see java.util.List#add(int, java.lang.Object) */
    public final void add(final int index, final T element) {
        add(element);
    }
    /** @see java.util.List#addAll(int, java.util.Collection) */
    public final boolean addAll(final int index, final Collection<? extends T> c) {
        return addAll(c);
    }
    /** @see java.util.List#get(int) */
    public final T get(final int index) {
        return list.get(index);
    }
    /** @see java.util.List#indexOf(java.lang.Object) */
    public final int indexOf(final Object o) {
        return list.indexOf(o);
    }
    /** @see java.util.List#lastIndexOf(java.lang.Object) */
    public final int lastIndexOf(final Object o) {
        return list.lastIndexOf(o);
    }
    /** @see java.util.List#listIterator() */
    public final ListIterator<T> listIterator() {
        return list.listIterator();
    }
    /** @see java.util.List#listIterator(int) */
    public final ListIterator<T> listIterator(final int index) {
        return list.listIterator(index);
    }
    /** @see java.util.List#remove(int) */
    public final T remove(final int index) {
        return list.remove(index);
    }
    /** @see java.util.List#set(int, java.lang.Object) */
    public final T set(final int index, final T element) {
        list.add(element);
        int i = list.indexOf(element);
        if (i > 0) {
            return list.get(i - 1);
        } else {
            return null;
        }
    }
    /** @see java.util.List#subList(int, int) */
    public final DynamicSortedCollection<T> subList(
            final int fromIndex, final int toIndex) {
        final DynamicSortedCollection<T> subList = 
            new DynamicSortedCollection<T>(comparator);
        subList.addAll(list.subList(fromIndex, toIndex));
        return subList;
    }
    /** @see java.lang.Object#equals(java.lang.Object) */
    public final boolean equals(final Object obj) {
        return list.equals(obj);
    }
    
    /** @see java.lang.Object#hashCode() */
    public final int hashCode() {
        return list.hashCode();
    }
}

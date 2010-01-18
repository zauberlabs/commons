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
import java.util.List;
import java.util.ListIterator;

/**
 * Based on {@link OnModificationProxyCollection}.
 * Implementation for the {@link List} interface.
 *
 * @author Pablo Grigolatto
 * @since Sep 23, 2009
 * @param <T> entity type
 */
public abstract class OnModificationProxyList<T>
    extends OnModificationProxyCollection<T>
    implements List<T> {

    /** @see List#add(int, Object) */
    public final void add(final int index, final T element) {
        getTarget().add(index, element);
    }

    /** @see List#addAll(int, Collection) */
    public final boolean addAll(final int index, final Collection<? extends T> c) {
        return getTarget().addAll(index, c);
    }

    /** @see List#get(int) */
    public final T get(final int index) {
        return getTarget().get(index);
    }

    /** @see List#indexOf(Object) */
    public final int indexOf(final Object o) {
        return getTarget().indexOf(o);
    }

    /** @see List#lastIndexOf(Object) */
    public final int lastIndexOf(final Object o) {
        return getTarget().lastIndexOf(o);
    }

    /** @see List#listIterator() */
    public final ListIterator<T> listIterator() {
        return getTarget().listIterator();
    }

    /** @see List#listIterator(int) */
    public final ListIterator<T> listIterator(final int index) {
        return getTarget().listIterator(index);
    }

    /** @see List#remove(int) */
    public final T remove(final int index) {
        return getTarget().remove(index);
    }

    /** @see List#set(int, Object) */
    public final T set(final int index, final T element) {
        return getTarget().set(index, element);
    }

    /** @see List#subList(int, int) */
    public final List<T> subList(final int fromIndex, final int toIndex) {
        return getTarget().subList(fromIndex, toIndex);
    }

    /** gets target list */
    @Override
    protected abstract List<T> getTarget();
    
}

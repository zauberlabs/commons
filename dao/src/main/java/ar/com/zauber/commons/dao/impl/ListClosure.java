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
package ar.com.zauber.commons.dao.impl;

import java.util.ArrayList;
import java.util.List;

import ar.com.zauber.commons.dao.Closure;


/**
 * {@link Closure} que guarda los resultados en una lista.
 * 
 * @author Juan F. Codagnone
 * @since Jan 22, 2007
 */
public class ListClosure<T> implements Closure<T> {
    /** closure */
    private final List<T> ret;
    
    /** default constructor */
    public ListClosure() {
        ret = new ArrayList<T>();
    }
    
    /** @param list an existant list */
    public ListClosure(final List<T> list) {
        if(list == null) {
            throw new IllegalArgumentException("list can't be null");
        }
        ret = list;
    }
    
    /**  @see Closure#execute(java.lang.Object) */
    public final void execute(final T t)  {
        ret.add(t);
    }
    
    /**
     * Returns the ret.
     * 
     * @return <code>List</code> with the ret.
     */
    public final List<T> getList() {
        return ret;
    }

    
}

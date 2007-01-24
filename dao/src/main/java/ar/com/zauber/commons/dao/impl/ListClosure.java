/*
 * Copyright (c) 2007 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;

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

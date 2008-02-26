/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.dao.impl;

import ar.com.zauber.commons.dao.Closure;


/**
 * Null implementation for {@link Closure}.
 * 
 * @author Juan F. Codagnone
 * @since Jan 24, 2007
 */
public class NullClosure<T> implements Closure<T> {
    /**  @see Closure#execute(java.lang.Object) */
    public final void execute(final T t) {
        // nothing to do
    }
}

/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.dao.closure;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.commons.dao.Transformer;

/**
 * {@link Closure} that transform it's input from type I to type T using a
 * {@link Transformer}, and then delegate the new object to a target {@link Closure}.
 * 
 * @author Juan F. Codagnone
 * @since Jun 23, 2009
 * @param <I> Input Type 
 * @param <T> Output Type
 */
public class TargetTransformerClosure<I, T> implements Closure<I> {
    private final Transformer<I, T> transformer;
    private final Closure<T> target;
    
    /** 
     * @param transformer transformer used to transform the input of the closure
     */
    public TargetTransformerClosure(final Transformer<I, T> transformer,
            final Closure<T> target) {
        Validate.notNull(transformer);
        Validate.notNull(target);
        
        this.transformer = transformer;
        this.target = target;
    }
    /** @see Closure#execute(Object) */
    public final void execute(final I input) {
        target.execute(transformer.transform(input));
    }
}

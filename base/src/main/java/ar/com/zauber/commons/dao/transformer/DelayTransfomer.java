/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.dao.transformer;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.AsyncronymousTransformer;
import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.commons.dao.Transformer;

/**
 * {@link Transformer} asyncronico.
 * 
 * @author Juan F. Codagnone
 * @since May 21, 2010
 * @param <I> tipo del parametro del input
 * @param <O> tipo del parametro de salida
 */
public class DelayTransfomer<I, O> implements AsyncronymousTransformer<I, O> {
    private final Transformer<I, O> transformer;

    /** Creates the SimpleAsyncronymousTransfomer. */
    public DelayTransfomer(final Transformer<I, O> transformer) {
        Validate.notNull(transformer);
        
        this.transformer = transformer;
    }
    
    /** @see AsyncronymousTransformer#transform(Object, Closure) */
    public final void transform(final I input, final Closure<O> closure) {
        closure.execute(transformer.transform(input));
    }

}

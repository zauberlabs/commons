/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.dao;

/**
 * An object capable of transforming an input object into some output object.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Jun 19, 2009
 * @param <I> Input entity class
 * @param <O> Output entity class
 */
public interface Transformer<I, O> {

    /** Transforms the input object (leaving it unchanged) into some output object.
     * @return the transformation of the input object to the output object
     */
   O transform(I input);
}

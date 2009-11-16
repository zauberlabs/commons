/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.conversion;


/**
 * 
 * Objects implementing this interface are capable of mapping a source object
 * to a destiny object of different type, taking into account a
 * <code>ConversionContext</code>.
 * 
 * Unlike the conversion done by a <code>Converter</code>, this kind of mapping
 * requires an existing instance of the target type.
 * 
 * @param <S>   Source class.
 * @param <T>   Target class.
 * 
 * @author Juan Edi
 * @author Mariano A. Cortesi
 * @since Nov 4, 2009
 */
public interface Mapper<S, T> {

    /**
     * 
     * Populates the target object taking into account the
     * <code>ConversionContext</code>
     * 
     * @param ctx contexto de conversion
     */
    void map(S source, T target, ConversionContext ctx);
    
}

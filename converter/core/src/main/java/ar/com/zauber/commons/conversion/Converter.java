/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.conversion;

/**
 * Converts an object to another one of a different type. Since a
 * <code>Converter</code>  represents a particular transformation, there
 * could be more than one <code>Converter</code> for any pair of types.
 * 
 * @param <S>
 *            Source type of the conversion.
 * @param <T>
 *            Target type of the conversion.
 * 
 * @author Mariano Cortesi
 * @since Nov 5, 2009
 */
public interface Converter<S, T> {

    /**
     * 
     * Converts the source object to a new instance of the target type,
     * getting from a <code>ConversionContext</code> any extra information
     * it may need.
     * 
     * @param ctx
     *            context of the conversion.
     */
    T convert(S source, ConversionContext ctx);
}

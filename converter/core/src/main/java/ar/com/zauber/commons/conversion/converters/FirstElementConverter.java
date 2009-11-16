/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.conversion.converters;

import java.util.List;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.conversion.ConversionContext;
import ar.com.zauber.commons.conversion.Converter;


/**
 * Converts a non-empty <code>List</code> to its first element. 
 * 
 * @param <T> Type of the elements of the <code>List</code>
 * @author Juan Edi
 * @since Nov 9, 2009
 */
public class FirstElementConverter <T>
                implements Converter<List<T>, T> {

    /** @Converter#convert(java.lang.Object, ConversionContext) */
    public final T convert(final List<T> source, final ConversionContext ctx) {
        Validate.notNull(source);
        Validate.notEmpty(source);
        return source.get(0);
    }

}

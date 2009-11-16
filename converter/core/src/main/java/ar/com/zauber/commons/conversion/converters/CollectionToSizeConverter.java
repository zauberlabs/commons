/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.conversion.converters;

import java.util.Collection;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.conversion.ConversionContext;
import ar.com.zauber.commons.conversion.Converter;


/**
 * Converter que convierte una lista en su tamaño
 * 
 * @author Juan Edi
 * @author Mariano A. Cortesi
 * @since Nov 3, 2009
 */
public class CollectionToSizeConverter implements Converter<Collection<?>, Integer> {

    /** @see Converter#convert(Object, ConversionContext) */
    public final Integer convert(final Collection<?> source,
            final ConversionContext ctx) {
        Validate.notNull(source);
        return source.size();
    }

}

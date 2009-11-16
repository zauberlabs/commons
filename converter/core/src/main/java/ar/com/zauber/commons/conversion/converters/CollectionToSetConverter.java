/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.conversion.converters;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.conversion.ConversionContext;
import ar.com.zauber.commons.conversion.Converter;


/**
 * Converts a <code>Collection</code> to a <code>Set</code> of elements of 
 * other type, applying an defined <code>Converter</code> for the elements. 
 * 
 * @param <S>
 *            Type of the elements of the source <code>Collection</code>
 * @param <T>
 *            Type of the elements of the target <code>Set</code>
 * 
 * @author Juan Edi
 * @since Nov 10, 2009
 */
public class CollectionToSetConverter<S, T>
        implements Converter<Collection<S>, Set<T>> {

    private Converter<S, T> elementConverter;

    /**
     * Creates the ListConverter.
     * 
     * @param elementConverter
     *            <code>Converter</code> to use for each element.
     */
    public CollectionToSetConverter(final Converter<S, T> elementConverter) {
        Validate.notNull(elementConverter);
        this.elementConverter = elementConverter;
    }

    /** @see Converter#convert(Object, ConversionContext) */
    public final Set<T> convert(final Collection<S> source,
                                final ConversionContext ctx) {
        Validate.notNull(source);
        Set<T> targetSet = new HashSet<T>();
        
        for (S s : source) {
            targetSet.add(elementConverter.convert(s, ctx));
        }
        
        return targetSet;
    }

}
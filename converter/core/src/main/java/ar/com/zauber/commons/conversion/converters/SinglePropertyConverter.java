/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.conversion.converters;

import ar.com.zauber.commons.conversion.Converter;

/**
 * Converts a property from a source object to one of the target object.
 * 
 * @param <S> source type of the conversion.
 * @param <SF> type of the property to be extracted from de source object.
 * @param <T> target type of the counversion.
 * @author Juan Edi
 * @since Nov 16, 2009
 */
@SuppressWarnings("unused")
public class SinglePropertyConverter<S, SF, T>
                                        extends CompositeConverter<S, SF, T> {

    private final String sourcePropertyName;
    private final Converter<SF, T> converter;
    
    
    /**
     * Creates the CompositeSinglePropertyConverter.
     *
     * @param converterA
     * @param converterB
     * @param sourcePropertyName
     * @param converter
     */
    public SinglePropertyConverter(final String sourcePropertyName,
                                    final Converter<SF, T> converter) {
        super(new PropertyExtractorConverter<S, SF>(sourcePropertyName),
                converter, false);
        this.sourcePropertyName = sourcePropertyName;
        this.converter = converter;
    }
    

    
}
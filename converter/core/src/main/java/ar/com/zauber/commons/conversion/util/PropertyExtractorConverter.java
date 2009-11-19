/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.conversion.util;

import org.apache.commons.lang.Validate;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.NullValueInNestedPathException;

import ar.com.zauber.commons.conversion.ConversionContext;
import ar.com.zauber.commons.conversion.Converter;

/**
 * Extracts a property value from an object
 * 
 * @param <S>
 * @param <SF>
 * @author Juan Edi
 * @since Nov 16, 2009
 */
public class PropertyExtractorConverter<S, SF> implements Converter<S, SF> {

    private final String propertyName;
    
    /**
     * Creates the PropertyExtractorConverter.
     *
     * @param propertyName
     */
    public PropertyExtractorConverter(final String propertyName) {
        super();
        this.propertyName = propertyName;
    }



    /** @see Converter#convert(java.lang.Object, ConversionContext) */
    @SuppressWarnings("unchecked")
    public final SF convert(final S source, final ConversionContext ctx) {
        Validate.notNull(source);
        SF propValue;
        try {
            propValue = (SF) new BeanWrapperImpl(source)
                    .getPropertyValue(propertyName);
        } catch (NullValueInNestedPathException e) {
            propValue = null;
        }
        return propValue;
    }

}

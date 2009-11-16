/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.conversion.config;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.conversion.Converter;


/**
 * Represents a field of the target object of the conversion to be populated, and
 * a converter that retrieves the value of the property from the source object of
 * the conversion. 
 * 
 * @param <S> Source type of the conversion.
 * @param <T> Target type of the conversion.
 *
 * @author Juan Edi
 * @since Nov 4, 2009
 */
public class ConversionField<S, T> {

    private final String targetName;
    private final Converter<S, T> converter;

    /**
     * Creates the SimpleConversionField.
     */
    public ConversionField(final String targetName,
            final Converter<S, T> converter) {
        Validate.notNull(targetName);
        Validate.notNull(converter);
        this.targetName = targetName;
        this.converter = converter;

    }

    /**
     * Internal converter used to get the final property value. 
     */
    public final Converter<S, T> getConverter() {
        return this.converter;
    }

    /**
     * Target's field name to be populated in the conversion.  
     */
    public final String getTargetFieldName() {
        return this.targetName;
    }

    /** @see java.lang.Object#toString() */
    @Override
    public final String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("[Field: ");
        buffer.append(this.targetName);
        buffer.append(" with: ");
        buffer.append(this.converter);
        buffer.append(" ]");
        return buffer.toString();
    }
}

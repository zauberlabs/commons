/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.conversion.converters;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.conversion.ConversionContext;
import ar.com.zauber.commons.conversion.Converter;


/**
 * Converter used in cases in which the target field is supposed to be the same
 * as the source field. <code>convert</code> method returns the source object.
 * 
 * @param <S> Type of the element to which the converter is applied..
 * 
 * @author Juan Edi
 * @since Nov 3, 2009
 */
public class IdentityConverter<S> implements Converter<S, S> {

    /** @see Converter#convert(Object, ConversionContext) */
    public final S convert(final S source, final ConversionContext ctx) {
        Validate.notNull(source);
        return source;
    }

    /** @see java.lang.Object#toString() */
    @Override
    public final String toString() {
        return "identityConverter";
    }
}

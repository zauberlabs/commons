/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.conversion.util;

import java.util.Date;

import ar.com.zauber.commons.conversion.ConversionContext;
import ar.com.zauber.commons.conversion.Converter;

/**
 * Convierte un date a un long
 * 
 * 
 * @author Juan F. Codagnone
 * @since Sep 2, 2010
 */
public class DateToLongConverter implements Converter<Date, Long> {

    /** @see Converter#convert(Object, ConversionContext) */
    public final Long convert(final Date source, final ConversionContext ctx) {
        return source.getTime();
    }
}

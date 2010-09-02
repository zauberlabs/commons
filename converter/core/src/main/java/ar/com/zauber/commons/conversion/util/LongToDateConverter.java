/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.conversion.util;

import java.util.Date;

import ar.com.zauber.commons.conversion.ConversionContext;
import ar.com.zauber.commons.conversion.Converter;

/**
 * Crea una fecha en base a un long
 * 
 * @author Juan F. Codagnone
 * @since Sep 2, 2010
 */
public class LongToDateConverter implements Converter<Long, Date> {

    /** @see Converter#convert(Object, ConversionContext) */
    public final Date convert(final Long source, final ConversionContext ctx) {
        return new Date(source);
    }
}

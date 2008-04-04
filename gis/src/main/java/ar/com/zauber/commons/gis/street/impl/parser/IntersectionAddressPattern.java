/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.gis.street.impl.parser;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ar.com.zauber.commons.gis.street.StreetsDAO;

/**
 * Representa algo como CALLE y CALLE
 * 
 * @author Christian Nardi
 * @since Sep 28, 2007
 */
public class IntersectionAddressPattern implements AddressPattern {
    /** expresion para detectar si un texto habla de intersección de
     * calles */
    public static final Pattern PATTERN = 
                        Pattern.compile("(" + STREET_NAME_PATTERN + "+)[yY]\\s+(" 
                            + STREET_NAME_PATTERN + "+)");
    /** @see ar.com.zauber.commons.gis.street.impl.parser.AddressPattern#
     * getAddressResult(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public final Collection getAddressResult(final String text, 
            final StreetsDAO streetsDAO) {
        final Matcher matcher = PATTERN.matcher(text);
        if (matcher.matches()) {
            return  streetsDAO.getIntersection(matcher.group(1), 
                    matcher.group(2));
        }
        return Collections.EMPTY_LIST;
    }

}

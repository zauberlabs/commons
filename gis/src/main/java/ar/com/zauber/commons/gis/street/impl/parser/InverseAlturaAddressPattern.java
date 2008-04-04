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
 * Representa algo como al ALTURA de CALLE
 * 
 * @author Christian Nardi
 * @since Sep 28, 2007
 */
public class InverseAlturaAddressPattern extends NormalAddressPattern {
    /** expresion regular que detecta si un texto es "ALTURA de CALLE" */
    public static final Pattern PATTERN = 
        Pattern.compile("([aA][lL]\\s)?\\s*(\\d+)\\s+[dD][eE]\\s+(" 
                + STREET_NAME_PATTERN + "+)");
    
    /** @see ar.com.zauber.commons.gis.street.impl.parser.AddressPattern#
     * getAddressResult(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public final Collection getAddressResult(final String text, 
            final StreetsDAO streetsDAO) {
        final String trimText = text.trim();
        final Matcher matcher = PATTERN.matcher(trimText);
        if (matcher.matches()) {
            return createResult(matcher.group(3), matcher.group(2), streetsDAO);
        }
        //Si no matcheo retorno null
        return Collections.EMPTY_LIST;
    }

}

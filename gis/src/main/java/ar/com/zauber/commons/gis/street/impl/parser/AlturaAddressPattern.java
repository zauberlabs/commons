/*
 * Copyright (c) 2007 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.gis.street.impl.parser;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ar.com.zauber.commons.gis.street.StreetsDAO;

/**
 * Representa algo como CALLE al ALTURA
 * 
 * @author Christian Nardi
 * @since Sep 28, 2007
 */
public class AlturaAddressPattern extends NormalAddressPattern {
    public final static Pattern PATTERN = 
        Pattern.compile("(" + STREET_NAME_PATTERN + "+)\\s+[aA][lL]\\s+(\\d+)");
    
    /** @see ar.com.zauber.commons.gis.street.impl.parser.AddressPattern#
     * getAddressResult(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public Collection getAddressResult(final String text, 
            final StreetsDAO streetsDAO) {
        final String trimText = text.trim();
        final Matcher matcher = PATTERN.matcher(trimText);
        if (matcher.matches()) {
            return createResult(matcher.group(1), matcher.group(2), streetsDAO);
        }
        //Si no matcheo retorno null
        return Collections.EMPTY_LIST;
    }

}

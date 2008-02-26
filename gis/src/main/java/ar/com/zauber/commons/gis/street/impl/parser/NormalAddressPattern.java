/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.gis.street.impl.parser;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ar.com.zauber.commons.gis.Result;
import ar.com.zauber.commons.gis.street.StreetsDAO;

/**
 * Representa algo como CALLE NUMERO
 * @author Christian Nardi
 * @since Sep 28, 2007
 */
public class NormalAddressPattern implements AddressPattern {
    private static final Pattern PATTERN = 
        Pattern.compile("(" + STREET_NAME_PATTERN + "+)\\s+(\\d+)");

    /** @see ar.com.zauber.commons.gis.street.impl.parser.AddressPattern#getAddressResult()
     */
    @SuppressWarnings("unchecked")
    public Collection<Result> getAddressResult(final String text, final 
            StreetsDAO streetsDAO) {
        final String trimText = text.trim();
        final Matcher matcher = PATTERN.matcher(trimText);
        if (matcher.matches()) {
            return createResult(matcher.group(1), matcher.group(2), streetsDAO);
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * @param streetsDAO 
     * @param group
     * @param group2
     * @return
     */
    @SuppressWarnings("unchecked")
    protected Collection createResult(final String calle, final String alturaText, 
            final StreetsDAO streetsDAO) {
      //intento obtener la altura
        Integer altura = null;
        try {
            altura = Integer.parseInt(alturaText);
        } catch (NumberFormatException e) {
          //todo deberia andar bien por la regex, pero por las dudas...
            return Collections.EMPTY_LIST;
        }
        if (altura == null) {
            return Collections.EMPTY_LIST;
        }
        //Construyo el resultado
        return streetsDAO.geocode(calle, altura.intValue());
    }
    
    
}

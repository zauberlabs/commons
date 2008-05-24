/*
 * Copyright (c) 2005-2008 Zauber S.A. <http://www.zauber.com.ar/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

    // CHECKSTYLE:DESIGN:OFF
    /** @see AddressPattern#getAddressResult() */
    @SuppressWarnings("unchecked")
    public  Collection<Result> getAddressResult(final String text, final 
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
  //CHECKSTYLE:DESIGN:ON
    
}

/**
 * Copyright (c) 2005-2011 Zauber S.A. <http://www.zaubersoftware.com/>
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

import ar.com.zauber.commons.gis.street.StreetsDAO;

/**
 * Representa algo como CALLE al ALTURA
 * 
 * @author Christian Nardi
 * @since Sep 28, 2007
 */
public class AlturaAddressPattern extends NormalAddressPattern {
    /** expresion que ... */
    public  static final Pattern PATTERN = 
        Pattern.compile("(" + STREET_NAME_PATTERN + "+)\\s+[aA][lL]\\s+(\\d+)");
    
    /** @see AddressPattern#getAddressResult(String) */
    @SuppressWarnings("unchecked")
    public final Collection getAddressResult(final String text, 
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

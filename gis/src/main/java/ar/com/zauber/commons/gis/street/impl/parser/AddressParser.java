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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ar.com.zauber.commons.gis.Result;
import ar.com.zauber.commons.gis.street.StreetsDAO;
import ar.com.zauber.commons.gis.street.model.address.IntersectionAddress;
import ar.com.zauber.commons.gis.street.model.address.NormalAddress;

/**
 * Class para parsear direcciones
 * 
 * @author Christian Nardi
 * @since Sep 28, 2007
 */
public final class AddressParser {
     
    private final Collection<AddressPattern> patterns;
    
    
    
    /**
     * Creates the AddressParser.
     *
     * @param patterns
     */
    public AddressParser(final Collection<AddressPattern> patterns) {
        super();
        this.patterns = patterns;
    }

    /**
     * Parsea el texto intentando matchear algun tipo de resultado
     * {@link IntersectionAddress} o {@link NormalAddress}
     * @param text
     * @param streetsDAO 
     * @param paging
     * @param dao
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Result> parse(final String text, final StreetsDAO streetsDAO) {
        final List ret = new ArrayList();
        final String trimText = text.trim();
        
        for (AddressPattern pattern : patterns) {
            final Collection result = pattern.getAddressResult(trimText, streetsDAO);
            if (result != null) {
                ret.addAll(result);
            }
        }
        return ret;
    }
    
}

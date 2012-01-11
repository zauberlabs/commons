/**
 * Copyright (c) 2005-2012 Zauber S.A. <http://www.zaubersoftware.com/>
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
package ar.com.zauber.commons.web.utils.impl;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import ar.com.zauber.commons.repository.misc.Nameable;
import ar.com.zauber.commons.web.utils.SEOIdStrategy;

/**
 * {@link SEOIdStrategy} que solo utiliza el id para identifcar la entidad
 * (no utiliza el nombre para nada)
 *  
 * @author Juan F. Codagnone
 * @since Feb 26, 2009
 */
public class IdSEOIdStrategy implements SEOIdStrategy {
    private final String allMark = "all";
    
    /** @see SEOIdStrategy#getIdFromSEOFriendly(String) */
    public final Serializable getIdFromSEOFriendly(final String l) {
        return StringUtils.isBlank(l) ? null : l.equals(allMark)
                ? null : Long.parseLong(l);
    }

    /** @see SEOIdStrategy#getSEOFriendly(Nameable) */
    public final String getSEOFriendly(final Nameable nameable) {
        return nameable == null ? allMark 
                      : nameable.getId() == null ? allMark 
                      : nameable.getId().toString();
    }
}

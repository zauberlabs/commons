/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.exception.NoSuchEntityException;
import ar.com.zauber.commons.repository.misc.Nameable;
import ar.com.zauber.commons.web.utils.SEOIdStrategy;

/**
 * concatena el id con el nombre de la entidad. Remplaza todos los caracteres
 * reservados y peligros para una URL por -.
 * 
 * @author Juan F. Codagnone
 * @since Feb 27, 2009
 */
public class IdNameSEOIdStrategy  implements SEOIdStrategy {
    private String allMark = "all";
    private String separator  = "-";
    
    /** @see SEOIdStrategy#getIdFromSEOFriendly(String) */
    public final Long getIdFromSEOFriendly(final String l) {
        final Long ret;
        
        if(StringUtils.isBlank(l)) {
            ret = null;
        } else if(l.equals(allMark)) {
            ret = null;
        } else {
            String [] fields = l.split("[" + separator + "]");
            if(fields.length > 0) {
                try {
                    ret = Long.parseLong(fields[0]);
                } catch(NumberFormatException e) {
                    throw new NoSuchEntityException(l); 
                }
            } else {
                throw new NoSuchEntityException(l); // falta algo
            }
        }
        return ret;
    }

    /** @see SEOIdStrategy#getSEOFriendly(Nameable) */
    public final String getSEOFriendly(final Nameable nameable) {
        final String ret;
        if(nameable == null) {
            ret = allMark;
        } else if(nameable.getId() == null) {
            ret = allMark;
        } else {
            String s =  nameable.getId().toString() 
                          + separator 
                          + nameable.getName().replaceAll(
                    "[.$&+,/:;=?@ <>#%{}|\\^~\\[\\]`]", "-");
            while(s.contains("--")) {
                s = s.replace("--", "-");
            }
            s = s.replace('á', 'a').replace('Á', 'A')
             .replace('é', 'e').replace('É', 'E')
             .replace('í', 'i').replace('Í', 'I')
             .replace('ó', 'o').replace('Ó', 'O')
             .replace('ú', 'u').replace('Ú', 'U')
             .replace('ñ', 'n').replace('Ñ', 'N')
             .replace('ü', 'u').replace('Ü', 'U');
            
            ret = s.toLowerCase();
        }
        return ret; 
                      
    }

    public final String getAllMark() {
        return allMark;
    }

    /** @see #getAllMark() */
    public final void setAllMark(final String allMark) {
        Validate.isTrue(!StringUtils.isBlank(allMark));
        this.allMark = allMark;
    }

    public final String getSeparator() {
        return separator;
    }

    /** @see #getSeparator() */
    public final void setSeparator(final String separator) {
        Validate.isTrue(!StringUtils.isBlank(separator));
        this.separator = separator;
    }
}

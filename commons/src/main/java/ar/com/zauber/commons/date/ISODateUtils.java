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
package ar.com.zauber.commons.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase de utilidad para ISO.
 * 
 * 
 * @author Juan F. Codagnone
 * @since May 16, 2009
 */
public final class ISODateUtils {

    /** Creates the ISODateUtils. */
    private ISODateUtils() {
        // utility class
    }
    
    private static final DateFormat ISO_DATE_FORMATTER = 
        new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Returns the isoDateFormater.
     * 
     * @return <code>DateFormat</code> with the isoDateFormater.
     */
    public static DateFormat getIsoDateFormater() {
        return ISO_DATE_FORMATTER;
    }
    
    /** Formatea una fecha en YYYY-MM-DD */
    public static String isoDateFormat(final Date date) {
        return ISO_DATE_FORMATTER.format(date);
    }
}
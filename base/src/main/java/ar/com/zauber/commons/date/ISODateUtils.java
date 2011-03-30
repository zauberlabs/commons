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
package ar.com.zauber.commons.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility Class to format and parse ISO Dates.
 * This utility class is thread-safe (SimpleDateFormat is not thread-safe)
 * 
 * @author Juan F. Codagnone
 * @since May 16, 2009
 */
public final class ISODateUtils {

    private static ThreadLocal<DateFormat> FORMATTER = new ThreadLocal<DateFormat>();
    
    /** Creates the ISODateUtils. */
    private ISODateUtils() {
        // utility class
    }
    
    /**
     * Returns the isoDateFormater.
     * this formatter is not lenient.
     * @return <code>DateFormat</code> with the isoDateFormater.
     * @deprecated The DateFormat is not thread-safe, and the ISO formatter should only be accessed through
     *      the utility methods.
     */
    @Deprecated
    public static DateFormat getIsoDateFormater() {
        return getFormatter();
    }
    
    /** Formatea una fecha en YYYY-MM-DD */
    public static String isoDateFormat(final Date date) {
        return getFormatter().format(date);
    }

    /** parse a iso date */
    public static Date parseIsoDate(final String string) {
        try {
            return getFormatter().parse(string);
        } catch (final ParseException e) {
            throw new IllegalArgumentException("`" + string 
                    + "' is not a valid ISO date", e);
        }
    }

    /** Creates a DateFormat with ISO Format */
    private static DateFormat createIsoDateFormatter() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setLenient(false);
        return formatter;
    }
    
    /** Retrieves the {@link DateFormat} for the current Thread */
    private static DateFormat getFormatter() {
        DateFormat formatter = FORMATTER.get();
        if (formatter == null) {
            formatter = createIsoDateFormatter();
            FORMATTER.set(formatter);
        }
        return formatter;
    }
}

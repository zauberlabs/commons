/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
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

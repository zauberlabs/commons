/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.spring.beans.factory.impl.util;

import java.util.Date;

import org.apache.commons.lang.Validate;

/**
 * Implementación de {@link DateTestInterface}
 * 
 * @author Cecilia Hagge
 * @since Oct 23, 2009
 */
public class DateTestClass implements DateTestInterface {

    private final Date date;
    
    /** Construye clase de prueba */
    public DateTestClass(final Date date) {
        Validate.notNull(date);
        this.date = date;
    }
    
    /** @see DateTestInterface#getDate() */
    public final Date getDate() {
        return date;
    }

}

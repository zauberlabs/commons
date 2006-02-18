/*
 * Copyright (c) 2005 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.secret.impl;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.secret.ExpirationDatePolicy;

/**
 * TODO Brief description. TODO Detail
 * 
 * @author Juan F. Codagnone
 * @since Jun 19, 2005
 * @param <T> type
 */
public class DaysExpirationDatePolicy<T> implements ExpirationDatePolicy<T> {

    /** number of days */
    private final int days;

    /**
     * Creates the DaysExpirationDatePolicy.
     * 
     * @param days
     *            numbers of days to add to the current date.
     */
    public DaysExpirationDatePolicy(final int days) {
        Validate.isTrue(days > 0);
        this.days = days;
    }

    /** @see ExpirationDatePolicy#getExpirationDate(Guest) */
    public final Date getExpirationDate(final T t) {
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, days);
        
        return date.getTime();
    }

}

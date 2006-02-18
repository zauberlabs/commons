/*
 * Copyright (c) 2005 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.secret.impl;

import java.util.Calendar;
import java.util.Date;

import ar.com.zauber.commons.secret.ExpirationDateValidator;

/**
 * Validates that the date is valid agains the computer clock
 * 
 * @author Juan F. Codagnone
 * @since Jun 19, 2005
 */
public class CurrentTimeExpirationDateValidator implements
        ExpirationDateValidator {

    /** @see ExpirationDateValidator#isValid(java.util.Date) */
    public final boolean isValid(final Date date) {
        return Calendar.getInstance().getTime().before(date);
    }
}

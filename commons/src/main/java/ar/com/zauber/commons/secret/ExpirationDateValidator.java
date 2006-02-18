/*
 * Copyright (c) 2005 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.secret;

import java.util.Date;

/**
 * Validates that dates didn't expired
 * 
 * @author Juan F. Codagnone
 * @since Jun 19, 2005
 */
public interface ExpirationDateValidator {

    /**
     * @param date
     *            the date to check
     * @return <code>true</code> if the date has not expired
     */
    boolean isValid(Date date);
}

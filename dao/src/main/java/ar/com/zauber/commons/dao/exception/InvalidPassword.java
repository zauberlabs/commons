/*
 * Copyright (c) 2006 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.dao.exception;


/**
 * Invalid Password
 * 
 * @author Juan F. Codagnone
 * @since Mar 6, 2006
 */
public class InvalidPassword extends RuntimeException {

    /**
     * Creates the InvalidPassword.
     *
     * @param message an explanation of why the the password is invalid
     */
    public InvalidPassword(final String message) {
        super(message);
    }
}

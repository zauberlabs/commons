/*
 * Copyright (c) ${year} Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.message.impl.mail;


/**
 * Exception that indicates the use of an invalid string 
 * for an Email.
 * 
 * @author Mariano A. Cortesi
 * @since 06-jun-2005
 */
public class InvalidEmailAddressFormatException extends RuntimeException {

    /**
     * Creates the InvalidEmailAddressFormatException.
     */
    public InvalidEmailAddressFormatException() {
        super();
    }

    /**
     * Creates the InvalidEmailAddressFormatException.
     *
     * @param message Exception Message
     */
    public InvalidEmailAddressFormatException(final String message) {
        super(message);
    }

    /**
     * Creates the InvalidEmailAddressFormatException.
     *
     * @param cause Cause of the exception
     */
    public InvalidEmailAddressFormatException(final Throwable cause) {
        super(cause);
    }
}

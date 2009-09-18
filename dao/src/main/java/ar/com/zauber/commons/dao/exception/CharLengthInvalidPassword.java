/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.dao.exception;

/**
 * Allows to identify an invalid length password
 * 
 * 
 * @author Cecilia Hagge
 * @since Sep 18, 2009
 */
public class CharLengthInvalidPassword extends InvalidPassword {

    private final int minLength;
    /**
     * Creates the CharLengthInvalidPassword.
     *
     * @param message an explanation of why the the password is invalid
     * @param minLength the minimum length that the password supports
     */
    public CharLengthInvalidPassword(final String message, final int minLength) {
        super(message);
        this.minLength = minLength;
    }
    
    public final int getMinLength() {
        return minLength;
    }

}

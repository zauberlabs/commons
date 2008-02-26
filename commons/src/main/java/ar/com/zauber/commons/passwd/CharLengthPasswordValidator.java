/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.passwd;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.exception.InvalidPassword;


/**
 * {@link PasswordValidator} que requiere que la password tenga por 
 * lo menos N letras.
 * 
 * @author Juan F. Codagnone
 * @since Mar 6, 2006
 */
public class CharLengthPasswordValidator implements PasswordValidator {
    /** min length */
    private final int minLength;

    /**
     * Creates the CharLengthPasswordValidator.
     *
     * @param minLength the minunLength
     */
    public CharLengthPasswordValidator(final int minLength) {
        Validate.isTrue(minLength  >= 0);
        
        this.minLength = minLength;
    }

    /** @see PasswordValidator#validate(java.lang.String) */
    public final void validate(final String password) throws InvalidPassword {
        if(password == null) {
            throw new InvalidPassword(
                    "la password debe contener alg�n caracter");
        } else if(password.length() < minLength) {
            throw new InvalidPassword("La password debe tener por lo menos una"
                    + " longitud de " + minLength + " caracteres");
        }
    }
}

/*
 * Copyright (c) 2006 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.passwd;


/**
 * Implementación nula de {@link PasswordEncoder}. Retorna la 
 * misma password que se le pasa.
 * 
 * @author Juan F. Codagnone
 * @since Apr 5, 2006
 */
public class NullPasswordEncoder implements PasswordEncoder {

    /** @see PasswordEncoder#encodePassword(java.lang.String) */
    public final String encodePassword(final String password) {
        return password;
    }

}

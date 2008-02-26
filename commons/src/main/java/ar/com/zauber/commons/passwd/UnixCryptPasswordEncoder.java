/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.passwd;


/**
 * Codifica password con la funcion unix Crypt()
 * 
 * @author Juan F. Codagnone
 * @since Oct 31, 2006
 */
public class UnixCryptPasswordEncoder implements PasswordEncoder {

    /**  @see PasswordEncoder#encodePassword(String) */
    public final String encodePassword(final String password) {
        return UnixCrypt.crypt(password);
    }
}

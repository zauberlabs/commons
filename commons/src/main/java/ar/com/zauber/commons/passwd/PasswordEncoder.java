/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.passwd;


/**
 * Encodes passwords
 * 
 * @author Juan F. Codagnone
 * @since Feb 27, 2006
 */
public interface PasswordEncoder {
    /**
     * @param password the password to encode
     * @return an encoded version of password
     */
    String encodePassword(final String password);
}

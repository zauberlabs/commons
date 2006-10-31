/*
 * Copyright (c) 2006 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.auth.acegi.password;
import org.acegisecurity.providers.encoding.Md5PasswordEncoder;

import ar.com.zauber.commons.passwd.PasswordEncoder;

/**
 * Encoder de password para usar en el atributo userPassword de un ldap.
 * 
 * En este caso, la password se hashea a md5 y este se codifica en base64.
 *  
 * @see http://www.ietf.org/rfc/rfc2307.txt
 * @author Juan F. Codagnone
 * @since Oct 30, 2006
 */
public class LdapUserPasswordPasswordEncoder implements PasswordEncoder {
    /** codificador */
    private final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
    
    /** Creates the LdapUserPasswordPasswordEncoder. */
    public LdapUserPasswordPasswordEncoder() {
        encoder.setEncodeHashAsBase64(true);
    }
    
    /**  @see PasswordEncoder#encodePassword(String) */
    public final String encodePassword(final String password) {
        
        return  "{MD5}" + encoder.encodePassword(password, null);
    }

}

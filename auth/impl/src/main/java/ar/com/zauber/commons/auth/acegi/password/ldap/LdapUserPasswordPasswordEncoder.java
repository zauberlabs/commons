/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.auth.acegi.password.ldap;
import org.apache.commons.lang.Validate;
import org.springframework.security.providers.encoding.Md5PasswordEncoder;

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
    private final org.springframework.security.providers.encoding.
        PasswordEncoder encoder;
    private final String algorithm;
    
    /** 
     * Creates the LdapUserPasswordPasswordEncoder.
     * @deprecated use {@link #LdapUserPasswordPasswordEncoder(
     * org.acegisecurity.providers.encoding.PasswordEncoder)} 
     */
    public LdapUserPasswordPasswordEncoder() {
        this(new Md5PasswordEncoder(), "{MD5}");
        ((Md5PasswordEncoder)encoder).setEncodeHashAsBase64(true);
    }
    
    /** constructor */
    public LdapUserPasswordPasswordEncoder(org.springframework.security.
            providers.encoding.PasswordEncoder encoder,
            final String algorithm) {
        Validate.notNull(encoder);
        Validate.notEmpty(algorithm);
        
        this.encoder = encoder;
        this.algorithm = algorithm;
    }
    
    /**  @see PasswordEncoder#encodePassword(String) */
    public final String encodePassword(final String password) {
        
        return  algorithm + encoder.encodePassword(password, null);
    }

}

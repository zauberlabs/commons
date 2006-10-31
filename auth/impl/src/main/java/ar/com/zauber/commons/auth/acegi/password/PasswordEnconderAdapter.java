/*
 * Copyright (c) 2006 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.auth.acegi.password;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.passwd.PasswordEncoder;


/**
 * Adapts {@link org.acegisecurity.providers.encoding.PasswordEncoder}
 * to {@link ar.com.zauber.geotag.impl.sql.PasswordEncoder}
 * 
 * @author Juan F. Codagnone
 * @since Feb 27, 2006
 */
public class PasswordEnconderAdapter implements PasswordEncoder {
    /** encoder to adapt */
    private final org.acegisecurity.providers.encoding.PasswordEncoder 
            encoder;
    
    /**
     * Creates the PasswordEnconderAdapter.
     *
     * @param encoder encoder to adapt
     */
    public PasswordEnconderAdapter(
            final org.acegisecurity.providers.encoding.PasswordEncoder 
            encoder) {
        Validate.notNull(encoder, "encoder");
        
        this.encoder = encoder;
    }
    
    /** @see PasswordEncoder#encodePassword(String) */
    public final String encodePassword(final String password) {
        return encoder.encodePassword(password, null);
    }
}
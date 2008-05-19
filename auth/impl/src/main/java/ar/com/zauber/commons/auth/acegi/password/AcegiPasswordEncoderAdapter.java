/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.auth.acegi.password;

import org.apache.commons.lang.Validate;
import org.springframework.dao.DataAccessException;
import org.springframework.security.providers.encoding.PasswordEncoder;

/**
 * The oposite of {@link PasswordEnconderAdapter}.
 *
 *
 * @author Juan F. Codagnone
 * @since Oct 31, 2007
 */
public class AcegiPasswordEncoderAdapter implements PasswordEncoder {
    private final ar.com.zauber.commons.passwd.PasswordEncoder passwordEncoder;

    /**
     * Creates the AcegiPasswordEncoder.
     *
     */
    public AcegiPasswordEncoderAdapter(
            final ar.com.zauber.commons.passwd.PasswordEncoder passwordEncoder) {
        Validate.notNull(passwordEncoder);

        this.passwordEncoder = passwordEncoder;
    }

    /** @see PasswordEncoder#encodePassword(String, Object) */
    public String encodePassword(final String rawPass, final Object salt)
            throws DataAccessException {
        
        return passwordEncoder.encodePassword(rawPass);
    }

    /** @see PasswordEncoder#isPasswordValid(String, String, Object) */
    public boolean isPasswordValid(final String encPass, final String rawPass, 
            final Object salt)
            throws DataAccessException {
        return encPass.equals(encodePassword(rawPass, salt));
    }

}

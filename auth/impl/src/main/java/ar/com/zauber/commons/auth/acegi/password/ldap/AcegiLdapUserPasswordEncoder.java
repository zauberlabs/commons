/*
 * Copyright (c) 2007 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.auth.acegi.password.ldap;

import java.util.Map;

import org.acegisecurity.providers.dao.DaoAuthenticationProvider;
import org.acegisecurity.providers.encoding.PasswordEncoder;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.Validate;
import org.springframework.dao.DataAccessException;

import ar.com.zauber.commons.dao.exception.NoSuchEntityException;

/**
 * <p>
 * Acegi {@link PasswordEncoder} usefull to use in 
 * {@link DaoAuthenticationProvider#setPasswordEncoder(PasswordEncoder)}
 * to autenticate user in an Ldap.
 * </p>
 *  <p>Tipically in an ldap the password password is encoded as
 *   <ul>
 *     <li>{MD5}asdasdsa==</li>
 *     <li>{CRYPT}asdsad</li>
 *     <li>...</li>
 *  </p>
 *<p> This {@link PasswordEncoder} suport choose the right encoder
 *    given the encoder.
 *</p>
 *
 * @author Juan F. Codagnone
 * @since Oct 31, 2007
 */
public class AcegiLdapUserPasswordEncoder implements PasswordEncoder {
    private final Map<String, PasswordEncoder> encoders;

    /**
     * constructor
     *
     * @param encoders
     */
    AcegiLdapUserPasswordEncoder(final Map<String, PasswordEncoder> encoders) {
        Validate.notNull(encoders);

        this.encoders = encoders;
    }

    /** @see PasswordEncoder#encodePassword(String, Object) */
    public final String encodePassword(final String rawPass, final Object salt)
            throws DataAccessException {

        throw new NotImplementedException("imposible to implement.");
    }

    /** @see PasswordEncoder#isPasswordValid(String, String, Object) */
    public final boolean isPasswordValid(final String encPass, 
            final String rawPass, final Object salt) throws DataAccessException {
        final LdapPassword ldapPass = new LdapPassword(encPass);
        
        return getEncoder(ldapPass).isPasswordValid(encPass, 
                rawPass, salt);
    }

    /** @return the encoder given the algorithm */
    private PasswordEncoder getEncoder(final LdapPassword ldapPassword) {
        final String algo = ldapPassword.getAlgorithm();
        final PasswordEncoder encoder = encoders.get(algo);
        if(encoder == null) {
            throw new NoSuchEntityException("No encoder available for algorithm: "
                    + algo);
        }
        
        return encoder;
    }
    
    private String password(String s) {
        return s;
    }
}



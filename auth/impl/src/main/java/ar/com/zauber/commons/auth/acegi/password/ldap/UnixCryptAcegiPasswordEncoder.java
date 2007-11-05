/*
 * Copyright (c) 2007 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.auth.acegi.password.ldap;

import org.acegisecurity.providers.encoding.PasswordEncoder;
import org.springframework.dao.DataAccessException;

import ar.com.zauber.commons.passwd.UnixCrypt;
import ar.com.zauber.commons.passwd.UnixCryptPasswordEncoder;

/**
 * Acegi encoder for {@link UnixCrypt}. 
 *
 * Zauber brother: {@link UnixCryptPasswordEncoder}.
 * 
 * @author Juan F. Codagnone
 * @since Oct 31, 2007
 */
public class UnixCryptAcegiPasswordEncoder implements PasswordEncoder {

    /**  @see PasswordEncoder#encodePassword(String, Object) */
    public final String encodePassword(final String rawPass, 
            final Object salt) throws DataAccessException {
        return UnixCrypt.crypt(salt.toString(), rawPass);
    }

    /**  @see PasswordEncoder#isPasswordValid(String, String, Object) */
    public final boolean isPasswordValid(final String encPass, 
            final String rawPass, final Object salt) 
            throws DataAccessException {
        LdapPassword p = new LdapPassword(encPass);
        final String s;
        if(p.getAlgorithm() != null && p.getAlgorithm().equals("CRYPT")) {
            s = p.getData();
        } else {
            s = encPass;
        }
        return UnixCrypt.matches(s, rawPass);
    }
}

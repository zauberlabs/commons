/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.auth.acegi.password.ldap;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.acegisecurity.providers.encoding.PasswordEncoder;
import org.springframework.dao.DataAccessException;

import ar.com.zauber.commons.dao.exception.NoSuchEntityException;

/**
 * Test for {@link AcegiLdapUserPasswordEncoder}.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Oct 31, 2007
 */
public class AcegiLdapUserPasswordEncoderTest extends TestCase {

    /** test */
    public final void testFoo() {
        final Map<String, PasswordEncoder> map = 
            new HashMap<String, PasswordEncoder>();
        map.put("A", new PasswordEncoder() {
            public String encodePassword(final String rawPass, final Object salt)
                    throws DataAccessException {
                return "{A}asdsad";
            }

            public boolean isPasswordValid(final String encPass, 
                    final String rawPass, final Object salt) 
                    throws DataAccessException {
                return encodePassword(rawPass, salt).equals(encPass);
            }
            
        });
        
        map.put("C", new PasswordEncoder() {
            public String encodePassword(final String rawPass, final Object salt)
                    throws DataAccessException {
                return "{C}asdsad";
            }
            public boolean isPasswordValid(final String encPass, 
                    final String rawPass, final Object salt) 
                    throws DataAccessException {
                return encodePassword(rawPass, salt).equals(encPass);
            }
        });
        
        final PasswordEncoder encoder = new AcegiLdapUserPasswordEncoder(map);
        assertTrue(encoder.isPasswordValid("{A}asdsad", "B", ""));
        assertTrue(encoder.isPasswordValid("{C}asdsad", "D", ""));
        try {
            assertTrue(encoder.isPasswordValid("{X}asdsad", "D", ""));
            fail();
        } catch(final NoSuchEntityException e) {
            // ok
        }
        
    }
}



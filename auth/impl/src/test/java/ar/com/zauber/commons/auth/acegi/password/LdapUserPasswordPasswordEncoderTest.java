/*
 * Copyright (c) 2006 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.auth.acegi.password;

import ar.com.zauber.commons.auth.acegi.password.ldap.LdapUserPasswordPasswordEncoder;
import junit.framework.TestCase;


/**
 * Tests for {@link LdapUserPasswordPasswordEncoder}.
 * 
 * @author Juan F. Codagnone
 * @since Oct 30, 2006
 */
public class LdapUserPasswordPasswordEncoderTest extends TestCase {
    /** encoder */
    private LdapUserPasswordPasswordEncoder encoder = 
        new LdapUserPasswordPasswordEncoder();
    
    /** Test method for encodePassword(java.lang.String) */
    public final void testEncodePassword() {
        assertEquals("{MD5}CY9rzUYh03PK3k6DJie09g==", encoder.encodePassword("test"));
        assertEquals("{MD5}XrY7u+Ae7tCTyyK7j1rNww==", encoder.encodePassword("hello world"));
    }

}

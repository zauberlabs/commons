/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ar.com.zauber.commons.auth.acegi.password.ldap;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.springframework.dao.DataAccessException;
import org.springframework.security.providers.encoding.PasswordEncoder;

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



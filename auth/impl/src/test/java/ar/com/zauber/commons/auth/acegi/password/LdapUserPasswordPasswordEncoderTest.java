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

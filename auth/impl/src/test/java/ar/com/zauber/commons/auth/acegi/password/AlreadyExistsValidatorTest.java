/**
 * Copyright (c) 2005-2015 Zauber S.A. <http://flowics.com/>
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

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import ar.com.zauber.commons.auth.acegi.password.ldap.AcegiLdapUserPasswordEncoder;
import ar.com.zauber.commons.auth.acegi.password.ldap.LdapUserPasswordPasswordEncoder;
import ar.com.zauber.commons.auth.acegi.password.ldap.UnixCryptAcegiPasswordEncoder;
import ar.com.zauber.commons.auth.mock.MockAuthenticationUser;
import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.commons.dao.exception.InvalidPassword;
import ar.com.zauber.commons.passwd.PasswordValidator;

/**
 * Test de {@link AlreadyExistsPasswordValidator}.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Apr 30, 2008
 */
public class AlreadyExistsValidatorTest extends TestCase {

    /** test */
    public final void testFoo() {
        final Map<String, PasswordEncoder>  map = 
            new HashMap<String, PasswordEncoder>();
        final Md5PasswordEncoder md5 = new Md5PasswordEncoder();
        md5.setEncodeHashAsBase64(true);
        final ShaPasswordEncoder sha = new ShaPasswordEncoder();
        sha.setEncodeHashAsBase64(true);
        map.put("MD5", new AcegiPasswordEncoderAdapter(
                new LdapUserPasswordPasswordEncoder(md5, "{MD5}")));
        map.put("SHA", new AcegiPasswordEncoderAdapter(
                new LdapUserPasswordPasswordEncoder(sha, "{SHA}")));
        map.put("CRYPT", new UnixCryptAcegiPasswordEncoder());
        
        
        final PasswordValidator validator = 
          new AlreadyExistsPasswordValidator<String>(
            new AlreadyExistsPasswordValidator.PasswordDAO<String>() {
                public void getPasswords(final String user,
                        final Closure<String> passwordClosure) {
                    final String [] password = {
                         "{SHA}mYALhdM4PjovtF630AZqSHmp2tA=", // hola
                         "{CRYPT}r63tqDQ3/7dsk",              // chau
                         "{MD5}YFfxPEluz3/Xd86555rihQ==",    // hey
                    };
                    for (final String passwd : password) {
                        passwordClosure.execute(passwd);
                    }
                }
    
                public void registerPassword(final String user,
                        final String codedPassword) {
                    throw new NotImplementedException(); 
                }
            
        }, new AcegiLdapUserPasswordEncoder(map), 
            new MockAuthenticationUser<String>("jaime"));
        
        
        try {
            validator.validate("hola");
            fail();
        } catch(final InvalidPassword p) {
            // ok
        }
        try {
            validator.validate("chau");
            fail();
        } catch(final InvalidPassword p) {
            // ok
        }
        
        try {
            validator.validate("hey");
            fail();
        } catch(final InvalidPassword p) {
            // ok
        }
        
        validator.validate("asdasd");
    }
}

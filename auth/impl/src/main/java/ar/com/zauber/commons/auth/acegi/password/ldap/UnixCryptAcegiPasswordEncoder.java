/*
 * Copyright (c) 2005-2008 Zauber S.A. <http://www.zauber.com.ar/>
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

import org.springframework.dao.DataAccessException;
import org.springframework.security.providers.encoding.PasswordEncoder;

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

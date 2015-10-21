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
package ar.com.zauber.commons.auth.acegi.password.ldap;

import java.util.Map;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.Validate;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.PasswordEncoder;

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
    public AcegiLdapUserPasswordEncoder(
            final Map<String, PasswordEncoder> encoders) {
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
}



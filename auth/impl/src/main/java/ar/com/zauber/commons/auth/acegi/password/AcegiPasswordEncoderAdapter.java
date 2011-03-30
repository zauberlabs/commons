/**
 * Copyright (c) 2005-2011 Zauber S.A. <http://www.zaubersoftware.com/>
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

import org.apache.commons.lang.Validate;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.encoding.PasswordEncoder;

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

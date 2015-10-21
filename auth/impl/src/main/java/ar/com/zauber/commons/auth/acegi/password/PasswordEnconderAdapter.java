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

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.passwd.PasswordEncoder;


/**
 * Adapts {@link org.acegisecurity.providers.encoding.PasswordEncoder}
 * to {@link ar.com.zauber.geotag.impl.sql.PasswordEncoder}
 * 
 * @author Juan F. Codagnone
 * @since Feb 27, 2006
 */
public class PasswordEnconderAdapter implements PasswordEncoder {
    /** encoder to adapt */
    private final org.springframework.security.authentication.encoding.PasswordEncoder 
            encoder;
    
    /**
     * Creates the PasswordEnconderAdapter.
     *
     * @param encoder encoder to adapt
     */
    public PasswordEnconderAdapter(
            final org.springframework.security.authentication.encoding.PasswordEncoder 
            encoder) {
        Validate.notNull(encoder, "encoder");
        
        this.encoder = encoder;
    }
    
    /** @see PasswordEncoder#encodePassword(String) */
    public final String encodePassword(final String password) {
        return encoder.encodePassword(password, null);
    }
}
/**
 * Copyright (c) 2005-2012 Zauber S.A. <http://www.zaubersoftware.com/>
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
package ar.com.zauber.commons.passwd;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.exception.CharLengthInvalidPassword;


/**
 * {@link PasswordValidator} que requiere que la password tenga por 
 * lo menos N letras.
 * 
 * @author Juan F. Codagnone
 * @since Mar 6, 2006
 */
public class CharLengthPasswordValidator implements PasswordValidator {
    /** min length */
    private final int minLength;

    /**
     * Creates the CharLengthPasswordValidator.
     *
     * @param minLength the minunLength
     */
    public CharLengthPasswordValidator(final int minLength) {
        Validate.isTrue(minLength  >= 0);
        
        this.minLength = minLength;
    }

    /** @see PasswordValidator#validate(String) */
    public final void validate(final String password) {
        if(password == null) {
            throw new CharLengthInvalidPassword(
                    "la password debe contener algún caracter", minLength);
        } else if(password.length() < minLength) {
            throw new CharLengthInvalidPassword
                ("La password debe tener por lo menos una"
                    + " longitud de " + minLength + " caracteres", minLength);
        }
    }
}

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
package ar.com.zauber.commons.dao.exception;

/**
 * Allows to identify an invalid length password
 * 
 * 
 * @author Cecilia Hagge
 * @since Sep 18, 2009
 */
public class CharLengthInvalidPassword extends InvalidPassword {

    private final int minLength;
    /**
     * Creates the CharLengthInvalidPassword.
     *
     * @param message an explanation of why the the password is invalid
     * @param minLength the minimum length that the password supports
     */
    public CharLengthInvalidPassword(final String message, final int minLength) {
        super(message);
        this.minLength = minLength;
    }
    
    public final int getMinLength() {
        return minLength;
    }

}

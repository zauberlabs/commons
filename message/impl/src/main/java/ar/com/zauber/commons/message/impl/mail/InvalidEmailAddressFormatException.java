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
package ar.com.zauber.commons.message.impl.mail;


/**
 * Exception that indicates the use of an invalid string 
 * for an Email.
 * 
 * @author Mariano A. Cortesi
 * @since 06-jun-2005
 */
public class InvalidEmailAddressFormatException extends RuntimeException {

    /**
     * Creates the InvalidEmailAddressFormatException.
     */
    public InvalidEmailAddressFormatException() {
        super();
    }

    /**
     * Creates the InvalidEmailAddressFormatException.
     *
     * @param message Exception Message
     */
    public InvalidEmailAddressFormatException(final String message) {
        super(message);
    }

    /**
     * Creates the InvalidEmailAddressFormatException.
     *
     * @param cause Cause of the exception
     */
    public InvalidEmailAddressFormatException(final Throwable cause) {
        super(cause);
    }
}

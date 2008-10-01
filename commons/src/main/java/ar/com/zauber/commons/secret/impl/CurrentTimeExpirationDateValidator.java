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
package ar.com.zauber.commons.secret.impl;

import java.util.Calendar;
import java.util.Date;

import ar.com.zauber.commons.secret.ExpirationDateValidator;

/**
 * Validates that the date is valid agains the computer clock
 * 
 * @author Juan F. Codagnone
 * @since Jun 19, 2005
 */
public class CurrentTimeExpirationDateValidator implements
        ExpirationDateValidator {

    /** @see ExpirationDateValidator#isValid(java.util.Date) */
    public final boolean isValid(final Date date) {
        return Calendar.getInstance().getTime().before(date);
    }

    /** @see ExpirationDateValidator#getNowInvalid() */
    public Date getNowInvalid() {
        return Calendar.getInstance().getTime();
    }
}

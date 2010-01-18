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
package ar.com.zauber.commons.secret.impl;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.secret.ExpirationDatePolicy;

/**
 * TODO Brief description. TODO Detail
 * 
 * @author Juan F. Codagnone
 * @since Jun 19, 2005
 * @param <T> type
 */
public class DaysExpirationDatePolicy<T> implements ExpirationDatePolicy<T> {

    /** number of days */
    private final int days;

    /**
     * Creates the DaysExpirationDatePolicy.
     * 
     * @param days
     *            numbers of days to add to the current date.
     */
    public DaysExpirationDatePolicy(final int days) {
        Validate.isTrue(days > 0);
        this.days = days;
    }

    /** @see ExpirationDatePolicy#getExpirationDate(Guest) */
    public final Date getExpirationDate(final T t) {
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, days);
        
        return date.getTime();
    }

}

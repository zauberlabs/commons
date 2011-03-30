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
package ar.com.zauber.commons.date.impl;

import java.util.Date;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.date.DateProvider;

/**
 * Implementación de {@link DateProvider} que retorna 
 * siempre la misma fecha inmutable
 * 
 * @author Pablo Grigolatto
 * @since Oct 6, 2009
 */
public class InmutableDateProvider implements DateProvider {

    private final Date date;
    
    /** Constructor */
    public InmutableDateProvider(final Date date) {
        Validate.notNull(date);
        this.date = date;
    }

    /** @see DateProvider#getDate() */
    public final Date getDate() {
        return new Date(date.getTime());
    }

}

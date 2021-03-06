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
package ar.com.zauber.commons.date.impl;

import java.util.Date;

import ar.com.zauber.commons.date.DateProvider;

/**
 * Implementación de {@link DateProvider} que retorna la fecha actual
 * 
 * @author Pablo Grigolatto
 * @since Oct 6, 2009
 */
public class CurrentDateProvider implements DateProvider {

    /** @see DateProvider#getDate() */
    public final Date getDate() {
        return new Date();
    }

}

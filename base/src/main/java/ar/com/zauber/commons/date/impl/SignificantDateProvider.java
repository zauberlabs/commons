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
import org.apache.commons.lang.time.DateUtils;

import ar.com.zauber.commons.date.DateProvider;

/**
 * Recorta la fecha luego del último digito significativo.
 * Por ejemplo: para 2010-03-03 19:54:22 podria retornar 2010-03-03 00:00:00
 * 
 * @author Pablo Grigolatto
 * @since Mar 19, 2010
 */
public class SignificantDateProvider implements DateProvider {

    private final DateProvider target;
    private final int field;

    /** Creates the CurrentSignificantDayDateProvider. */
    public SignificantDateProvider(
            final DateProvider target, final int field) {
        Validate.notNull(target);
        this.target = target;
        this.field = field;
    }
    
    /** @see DateProvider#getDate() */
    public final Date getDate() {
        return DateUtils.truncate(target.getDate(), field);
    }

}

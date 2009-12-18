/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
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
package ar.com.zauber.commons.spring.beans.factory.impl;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.spring.beans.factory.CaseBlock;

/**
 * Implementacion de {@link CaseBlock} que retorna el {@link Object} pasado por
 * parametro si los dos {@link String} son iguales. Es case sensitive.
 * 
 * @author Pablo Grigolatto
 * @since Dec 18, 2009
 */
public class EqualsStringCaseBlock implements CaseBlock {
    private final String value;
    private final String expected;
    private final Object object;

    /** Creates the EqualsStringCaseBlock. */
    public EqualsStringCaseBlock(final String value, final String expected,
            final Object object) {
        Validate.notNull(value);
        Validate.notNull(expected);
        Validate.notNull(object);
        
        this.value = value;
        this.expected = expected;
        this.object = object;
    }
    
    /** @see CaseBlock#evaluate() */
    public final boolean evaluate() {
        return value.equals(expected);
    }

    /** @see CaseBlock#getObject() */
    public final Object getObject() {
        return object;
    }
}
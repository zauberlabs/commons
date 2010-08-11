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
package ar.com.zauber.commons.validate;

import org.junit.Test;

/**
 * Pruebas sobre {@link Validate}
 * 
 * @author Pablo Grigolatto
 * @since May 31, 2010
 */
public class ValidateTest {

    private static final Double DOUBLE = new Double(123);
    private static final Object OBJECT = new Object();
    private static final String STRING = "string";

    /** multi-object validate */
    @Test
    public final void testEmpty() {
        Validate.notNull();
        Validate.notBlank();
    }
    
    /** multi-object not null */
    @Test
    public final void testNotNull() {
        Validate.notNull(STRING, OBJECT, DOUBLE);
    }
    
    /** multi-object not null */
    @Test(expected = IllegalArgumentException.class)
    public final void testNotNullEx() {
        final Object nullObject = null;
        Validate.notNull(STRING, nullObject, DOUBLE);
    }
    
    /** multi-object not null or blank */
    @Test
    public final void testBlank() {
        Validate.notBlank(STRING, " z ", DOUBLE);
    }
    
    /** multi-object not null or blank */
    @Test(expected = IllegalArgumentException.class)
    public final void testNotBlankBlankEx() {
        Validate.notBlank(STRING, "", DOUBLE);
    }
    
    /** multi-object not null or blank */
    @Test(expected = IllegalArgumentException.class)
    public final void testNotBlankNullEx() {
        Validate.notBlank(STRING, null, DOUBLE);
    }
    
    /** multi-object not null or blank */
    @Test(expected = IllegalArgumentException.class)
    public final void testNotBlankEmptyEx() {
        Validate.notBlank(STRING, "  ", DOUBLE);
    }
    
}

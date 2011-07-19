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
package ar.com.zauber.commons.validate;

import static org.junit.Assert.*;

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
    
    /** true expression */
    @Test
    public final void testIsTrueWithTrueExpression() throws Exception {
        Validate.isTrue(3 > 2, "%d > %d", 3, 2);
    }
    
    /** false expression with format message */
    @Test(expected = IllegalArgumentException.class)
    public final void testIsTrueWithFalseExpressionAndMessage() throws Exception {
        try {
            Validate.isTrue(2 > 3, "%d > %d", 2, 3);
        } catch (final IllegalArgumentException e) {
            assertEquals("2 > 3", e.getMessage());
            throw e;
        }
    }
    
    /** false expression with null format message */
    @Test(expected = IllegalArgumentException.class)
    public final void testIsTrueWithFalseExpressionAndNullMessage() throws Exception {
            Validate.isTrue(2 > 3, null);
    }
    
    /** false expression */
    @Test
    public final void testIsFalseWithFalseExpression() throws Exception {
        Validate.isFalse(2 > 3, "%d > %d", 2, 3);
    }
    
    /** true expression with format message */
    @Test(expected = IllegalArgumentException.class)
    public final void testIsFalseWithTrueExpressionAndMessage() throws Exception {
        try {
            Validate.isFalse(3 > 2, "%d > %d", 3, 2);
        } catch (final IllegalArgumentException e) {
            assertEquals("3 > 2", e.getMessage());
            throw e;
        }
    }
    
    /** true expression with null format message */
    @Test(expected = IllegalArgumentException.class)
    public final void testIsFalseWithTrueExpressionAndNullMessage() throws Exception {
            Validate.isFalse(3 > 2, null);
    }
}

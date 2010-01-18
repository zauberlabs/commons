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
package ar.com.zauber.commons.dao.impl;

import junit.framework.TestCase;
import ar.com.zauber.commons.dao.predicate.EqualsPredicate;
import ar.com.zauber.commons.dao.predicate.IgnoreCaseEqualsPredicate;

/**
 * Test
 * 
 * @author Juan F. Codagnone
 * @since Jul 12, 2008
 */
public class IgnoreCaseEqualsPredicateTest extends TestCase {

    /** test */
    public final void testTrue() {
        assertTrue(new IgnoreCaseEqualsPredicate("abc").evaluate("ABC"));
        assertTrue(new IgnoreCaseEqualsPredicate("abc").evaluate("abc"));
    }
    
    /** test */
    public final void testFalse() {
        assertFalse(new IgnoreCaseEqualsPredicate("abc").evaluate("asd"));
    }
    
    /** test */
    public final void testNull() {
        assertFalse(new EqualsPredicate("abc").evaluate(null));
    }
    
    /** test */
    public final void testNullTarget() {
        try {
            new EqualsPredicate(null);
            fail();
        } catch(IllegalArgumentException e) {
            // ok
        }
    }
}

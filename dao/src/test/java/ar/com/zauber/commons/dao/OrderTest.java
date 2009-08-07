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
package ar.com.zauber.commons.dao;

import junit.framework.TestCase;

/**
 * Tests Order
 * 
 * 
 * @author Juan F. Codagnone
 * @since Jan 28, 2009
 */
public class OrderTest extends TestCase {

    /** test */
    public final void testSimpleConstructor() {
        final Order o = new Order("description");
        assertEquals("description", o.getProperty());
        assertTrue(o.getAscending());
        assertFalse(o.isIgnoreCase());
    }
    
    /** test */
    public final void testAscendingConstructor() {
        Order o = new Order("description", true);
        assertEquals("description", o.getProperty());
        assertTrue(o.getAscending());
        assertFalse(o.isIgnoreCase());
        
        o = new Order("description", false);
        assertEquals("description", o.getProperty());
        assertFalse(o.getAscending());
        assertFalse(o.isIgnoreCase());
    }
    
    
    /** test */
    public final void testToString() {
        assertEquals("description ASC", new Order("description", true).toString());
        assertEquals("user DESC", new Order("user", false).toString());
        assertEquals("user DESC ignoring case", 
                new Order("user", false, true).toString());
    }
    
    
    /** test */
    public final void testEquals() {
        assertEquals(new Order("username"), new Order("username"));
        assertEquals(new Order("username").hashCode(), 
                new Order("username").hashCode());
        assertFalse(new Order("username", true).equals(
                new Order("username", false)));
    }
}

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
package ar.com.zauber.commons.gis.street;

import junit.framework.TestCase;

/**
 * @author epere4
 *
 */
public class OptionsTest extends TestCase {

    /** Test method for {@link Options#filter(String)}. */
    public final void testIgnoreCommonWordsFilter() {
        assertEquals("", Options.IGNORE_COMMON_WORDS.filter("avenida"));
        assertEquals("nombre calle", 
                Options.IGNORE_COMMON_WORDS.filter("nombre calle"));
        assertEquals("nombre calle", 
                Options.IGNORE_COMMON_WORDS.filter("nombre avenida calle"));
        assertEquals("nombre calle ", 
                Options.IGNORE_COMMON_WORDS.filter("nombre calle avenida"));
        assertEquals("nombre calle", 
                Options.IGNORE_COMMON_WORDS.filter("avenida nombre calle"));
        assertEquals("nombre calle    ", 
                Options.IGNORE_COMMON_WORDS.filter("nombre calle    avenidA"));
        assertEquals("        avenidanombre calle", 
                Options.IGNORE_COMMON_WORDS.filter("        avenidanombre calle"));
        assertEquals("        nombre calle", 
                Options.IGNORE_COMMON_WORDS.filter("        avenida nombre calle"));
        assertEquals("        nombre calle ", Options.IGNORE_COMMON_WORDS.filter(
                "        avenida nombre calle avenida"));
        assertEquals("nombre calle ", Options.IGNORE_COMMON_WORDS.filter(
                "nombre calle Avenida avenida"));
        assertEquals("nombre calle avenidaavenida", 
                Options.IGNORE_COMMON_WORDS.filter("nombre calle avenidaavenida"));
    }
    
    /** test */
    public final void testRemoveExtraSpaces() {
        assertEquals("a a", Options.REMOVE_EXTRA_SPACES.filter("a a"));
        assertEquals("", Options.REMOVE_EXTRA_SPACES.filter("       "));
        assertEquals("a", Options.REMOVE_EXTRA_SPACES.filter("a       "));
        assertEquals("a b", Options.REMOVE_EXTRA_SPACES.filter("a   b    "));
        assertEquals("a a", Options.REMOVE_EXTRA_SPACES.filter("    a    a    "));
        assertEquals("a a", Options.REMOVE_EXTRA_SPACES.filter("    a    a"));
        assertEquals("a a", Options.REMOVE_EXTRA_SPACES.filter("a    a    "));
        assertEquals("addda", Options.REMOVE_EXTRA_SPACES.filter("    addda    "));
        assertEquals("a a", Options.REMOVE_EXTRA_SPACES.filter("    a\t\na    "));
        assertEquals("a a", Options.REMOVE_EXTRA_SPACES.filter(" a    a  "));
        assertEquals("a b c", Options.REMOVE_EXTRA_SPACES.filter("\t\na\t \nb\tc"));
    }
    
}

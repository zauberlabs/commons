/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
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

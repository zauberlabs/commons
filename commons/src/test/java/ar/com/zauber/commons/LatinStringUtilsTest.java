/*
 * Copyright (c) 2006 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons;

import junit.framework.TestCase;


/**
 * Testeo de unidad para {@link LatinStringUtils}.
 * 
 * @author Juan F. Codagnone
 * @since Aug 13, 2006
 */
public class LatinStringUtilsTest extends TestCase {

    /** test de unidad */
    public final void testReplaceAccent() {
        assertEquals("hola", LatinStringUtils.replaceAccents("hol·"));
        assertEquals("aeioun", LatinStringUtils.replaceAccents("·ÈÌÛ˙Ò"));
        assertEquals("AEIOUN", LatinStringUtils.replaceAccents("¡…Õ”⁄—"));
    }
}

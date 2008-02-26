/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.web;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;


/**
 * Unit test for {@link ar.com.zauber.commons.web.URIDirectory}
 *
 * @author Juan F. Codagnone
 * @since Oct 4, 2005
 */
public class URIDirectoryTest extends TestCase {

    /** unit test */
    public final void testWhole() {
        final String base = "eventz/";
        
        try {
            new URIDirectory(base, new HashMap<String, String>())
                    .getLinkFor("");
            fail();
        } catch(IllegalArgumentException e) {
            // ok
        }
        Map<String, String> map = new HashMap<String, String>();
        String s = "paranparan";
        map.put(s, "/home/ble");
        map.put("no", "no");
        final URIDirectory directory = new URIDirectory(base, map);
        assertEquals("eventz/home/ble", directory.getLinkFor(s));
        assertEquals("eventz/no", directory.getLinkFor("no"));
        
    }
}

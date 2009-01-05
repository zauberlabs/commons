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

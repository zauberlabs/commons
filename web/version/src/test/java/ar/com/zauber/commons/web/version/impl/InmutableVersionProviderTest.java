/**
 * Copyright (c) 2005-2015 Zauber S.A. <http://flowics.com/>
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
package ar.com.zauber.commons.web.version.impl;

import junit.framework.TestCase;

/**
 * tests {@link InmutableVersionProvider}.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Jul 19, 2008
 */
public class InmutableVersionProviderTest extends TestCase {

    /** test */
    public final void testConstructor() {
        try {
            new InmutableVersionProvider(null);
            fail();
        } catch(IllegalArgumentException e) { 
            // ok
        }
    }
    
    /** test */
    public final void testVersionProvider()  {
        assertEquals("1.2", new InmutableVersionProvider("1.2").getVersion());
    }
}

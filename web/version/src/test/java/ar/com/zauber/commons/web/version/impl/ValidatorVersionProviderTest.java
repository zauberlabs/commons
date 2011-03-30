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
package ar.com.zauber.commons.web.version.impl;

import junit.framework.TestCase;
import ar.com.zauber.commons.web.version.VersionProvider;
import ar.com.zauber.commons.web.version.impl.EmptyVersionProvider;
import ar.com.zauber.commons.web.version.impl.InmutableVersionProvider;
import ar.com.zauber.commons.web.version.impl.ValidatorVersionProvider;

/**
 * Tests for {@link ValidatorVersionProvider}.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Jul 19, 2008
 */
public class ValidatorVersionProviderTest extends TestCase {

    /** test */
    public final void testContructor() {
        try {
            new ValidatorVersionProvider(null);
            fail();
        } catch(IllegalArgumentException e) {
            // ok
        }
    }
    
    /** test */
    public final void testFailSpace() {
        try {
            new ValidatorVersionProvider(new InmutableVersionProvider(
                    "hola mundo")).getVersion();
            fail();
        } catch(IllegalArgumentException e) {
            // ok
        }
    }
    
    /** test */
    public final void testFailUnsafe() {
        try {
            new ValidatorVersionProvider(new InmutableVersionProvider(
                    "hola+mundo")).getVersion();
            fail();
        } catch(IllegalArgumentException e) {
            // ok
        }
    }
    
    /** test */
    public final void testNull() {
        try {
            new ValidatorVersionProvider(new VersionProvider() {
                public String getVersion() {
                    return null;
                }
            }).getVersion();
            fail();
        } catch(IllegalArgumentException e) {
            // ok
        }
    }
        
    /** test */
    public final void testSafe() {
        final String s = 
         "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_.!~";
        assertEquals(s, new ValidatorVersionProvider(
                new InmutableVersionProvider(s)).getVersion());
    }
    
    
    /** test */
    public final void testEmpty() {
        final String s = "";
        assertEquals(s, new ValidatorVersionProvider(
                new EmptyVersionProvider()).getVersion());
    }
}

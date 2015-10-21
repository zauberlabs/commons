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

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Manifest;

import junit.framework.TestCase;
import ar.com.zauber.commons.web.version.VersionProvider;
import ar.com.zauber.commons.web.version.impl.ManifestVersionProvider;

/**
 * test de {@link ManifestVersionProvider}.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Jul 19, 2008
 */
public class ManifestVersionProviderTest extends TestCase {

    /** test */
    public final void testNullArgument() {
        try {
            new ManifestVersionProvider(null, "");
            fail();
        } catch(IllegalArgumentException e) {
            // ok
        }
    }
    
    /** test   @throws IOException on error */
    public final void testAttributeExists() throws IOException {
        final InputStream is = getClass().getResourceAsStream("test-MANIFEST.MF");
        try {
            VersionProvider vp = new ManifestVersionProvider(new Manifest(is),
                    "Hudson-Build-Number");
            assertEquals("25", vp.getVersion());
        } finally {
            is.close();
        }
    }
    
    /** test   @throws IOException on error */
    public final void testAttributeDoesNotExists() throws IOException {
        final InputStream is = getClass().getResourceAsStream("test-MANIFEST.MF");
        try {
            VersionProvider vp = new ManifestVersionProvider(new Manifest(is),
                    "Blabla");
            assertEquals("", vp.getVersion());
        } finally {
            is.close();
        }
    }
}

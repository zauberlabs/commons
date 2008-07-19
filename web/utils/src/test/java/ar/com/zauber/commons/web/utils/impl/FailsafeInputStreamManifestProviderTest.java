/*
 * Copyright (c) 2005-2008 Zauber S.A. <http://www.zauber.com.ar/>
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
package ar.com.zauber.commons.web.utils.impl;

import java.io.InputStream;
import java.util.jar.Manifest;

import junit.framework.TestCase;

/**
 * Tests form {@link FailsafeInputStreamManifestProvider}.
 * 
 * @author Juan F. Codagnone
 * @since Jul 19, 2008
 */
public class FailsafeInputStreamManifestProviderTest extends TestCase {

    /** test */
    public final void testInvalid() {
        final Manifest m = new FailsafeInputStreamManifestProvider(
                (InputStream)null).getManifest();
        assertNotNull(m);
        assertTrue(m.getEntries().isEmpty());
        assertTrue(m.getMainAttributes().isEmpty());
    }
    
    /** test */
    public final void testValid() {
        final Manifest m = new FailsafeInputStreamManifestProvider(
                getClass().getResourceAsStream("test-MANIFEST.MF")).getManifest();
        assertNotNull(m);
        assertTrue(m.getEntries().isEmpty());
        assertEquals(8, m.getMainAttributes().size());
    }
}

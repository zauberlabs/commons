/**
 * Copyright (c) 2005-2012 Zauber S.A. <http://www.zaubersoftware.com/>
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
package ar.com.zauber.commons.web.transformation.censors;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import ar.com.zauber.commons.web.transformation.censors.impl.FalseCensorAccess;

/**
 * Tests for {@link CensorAccess}.
 * 
 * 
 * @author Matías Tito
 * @since Nov 11, 2008
 */
public class FalseCensorAccessTest {

    /** un censor access no puede recibir uris relativas */
    @Test(expected = IllegalArgumentException.class)
    public final void testRelativeUri() {
        new FalseCensorAccess().canAccess("hola");
    }
    
    /** un censor access no puede recibir uris relativas */
    @Test
    public final void testURLHttp() {
        assertFalse(new FalseCensorAccess().canAccess("http://flof.com.ar/"));
    }
    
    /** un censor access no puede recibir uris relativas */
    @Test
    public final void testURLHttps() {
        assertFalse(new FalseCensorAccess().canAccess("https://mail.google.com/"));
    }
    
    /** un censor access no puede recibir uris relativas */
    @Test
    public final void testURLftp() {
        assertFalse(new FalseCensorAccess().canAccess("ftp://mail.google.com/"));
    }
}

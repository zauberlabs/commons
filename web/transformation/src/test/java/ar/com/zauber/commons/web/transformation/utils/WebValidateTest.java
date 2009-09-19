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
package ar.com.zauber.commons.web.transformation.utils;

import org.junit.Test;

/**
 * TODO Descripcion de la clase. Los comenterios van en castellano.
 * 
 * 
 * @author Alejandro Souto
 * @since 12/11/2008
 */
public class WebValidateTest {

    /** test */
    @Test(expected = IllegalArgumentException.class)
    public final void testUriNotBlank() {
        WebValidate.uriNotBlank("");
    }

    /** test */
    @Test(expected = IllegalArgumentException.class)
    public final void testUriNotNull() {
        WebValidate.uriNotNull(null);
    }

    /** test */
    @Test(expected = IllegalArgumentException.class)
    public final void testUriWithLastSlash() {
        WebValidate.uriWellFormed("   /no/deberia/validar/   ");
    }
}

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
package ar.com.zauber.commons.web.uri.factory;

import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


/**
 * {@link VersionedUriFactory} Test Case
 * 
 * @author Mariano Cortesi
 * @since May 10, 2010
 */
public class VersionedUriFactoryTest {

    private UriFactory uriFactory;

    /** Initialize uriFactory */
    @Before
    public final void createUriFactory() {
        uriFactory = new VersionedUriFactory("0.3", new IdentityUriFactory());
    }
    
    /** test case */
    @Test
    public final void testEmptyString() {
        assertEquals("?v=0.3", uriFactory.buildUri(""));
    }

    /** test case */
    @Test
    public final void testNoSlash() {
        assertEquals("noslash?v=0.3", uriFactory.buildUri("noslash"));
    }
    
    /** test case */
    @Test
    public final void testNoQueryParameters() {
        assertEquals("/home?v=0.3", uriFactory.buildUri("/home"));
        assertEquals("/?v=0.3", uriFactory.buildUri("/"));
    }
    
    /** test case */
    @Test
    public final void testWithQueryParameters() {
        assertEquals("/home?good=true&v=0.3", 
                uriFactory.buildUri("/home?good=true"));
    }
}

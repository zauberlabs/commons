/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;


/**
 * Test de {@link RelativePathUriFactory} 
 * @author Mariano Semelman
 * @since Jul 7, 2010
 */
public class RelativePathUriFactoryTest  {
    private RelativePathUriFactory uriFactory;
    private MockHttpServletRequest request;
    
    
    /** */
    @Before
    public final void initialize() {
        request = new MockHttpServletRequest();
        uriFactory = new RelativePathUriFactory(new IdentityUriFactory(),
                "utf-8", new MutableRequestProvider(request));
    }
    
    /** Test de inicializacion */
    @Before
    public final void setUp() throws Exception {
        new RelativePathUriFactory(new IdentityUriFactory(), "utf-8",
                new MutableRequestProvider(new MockHttpServletRequest("GET", "/")));
    }
    
    /** Test de construccion de uri */
    @Test
    public final void buildUri() throws Exception {
        request.setRequestURI("/abc/123/abc/search?q='hola'");
        Assert.assertEquals("../../../foo", uriFactory.buildUri("foo", request));
        Assert.assertEquals("../../../foo", uriFactory.buildUri("/foo", request));
    }
    
    /** Test de construccion de uri  sin slash inicial.*/
    @Test
    public final void buildUri2() throws Exception {
        request.setRequestURI("abc/123/abc/search?q='hola'");
        Assert.assertEquals("../../../foo", uriFactory.buildUri("foo", request));
        Assert.assertEquals("../../../foo", uriFactory.buildUri("/foo", request));
    }
    
    /** Test que revisa si el request esta dado en el mismo segmento */
    @Test
    public final void testRequestOnContext() {
        request.setRequestURI("/search?q='hola'");
        Assert.assertEquals("./foo", uriFactory.buildUri("foo", request));
        Assert.assertEquals("./foo", uriFactory.buildUri("/foo", request));

        request.setRequestURI("/linea/search?q='hola'");
        Assert.assertEquals("../linea/foo", uriFactory.buildUri(
                "/linea/foo", request));        
    }
}

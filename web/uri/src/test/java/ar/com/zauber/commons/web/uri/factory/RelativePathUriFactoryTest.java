/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
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

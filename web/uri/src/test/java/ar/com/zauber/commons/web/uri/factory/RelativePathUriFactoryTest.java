/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.factory;
import static ar.com.zauber.commons.web.uri.factory.RelativePathUriFactory.*;
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
        Assert.assertEquals("./foo", uriFactory.buildUri(
                "/linea/foo", request));        
    }
    
    /** Test que revisa si el request esta dado en el mismo segmento */
    @Test
    public final void testRelative() {
        request.setRequestURI("/api/v1/u/juan/entidad-by-cia-tc/814,R");
        Assert.assertEquals("../saldo/814,R,80004", uriFactory.buildUri(
                "api/v1/u/juan/saldo/814,R,80004", request));        
    }
    

    /** Test que revisa si el request esta dado en el mismo segmento */
    @Test
    public final void testComunDenominador1() {
        final String []s = comunDenominador(
                "/api/v1/u/juan/entidad-by-cia-tc/814,R", 
                "/api/v1/u/juan/saldo/814,R,80004");
        Assert.assertEquals("/entidad-by-cia-tc/814,R", s[0]);
        Assert.assertEquals("/saldo/814,R,80004", s[1]);
    }

    /** Test que revisa si el request esta dado en el mismo segmento */
    @Test
    public final void testComunDenominador2() {
        final String []s = comunDenominador(
                "/api/v1/u/juan/entidad-by-cia-tc/814,R", 
                "/api/v1/u/juan/entidad-by-cia-tc-lalala/814,R,80004");
        Assert.assertEquals("/entidad-by-cia-tc/814,R", s[0]);
        Assert.assertEquals("/entidad-by-cia-tc-lalala/814,R,80004", s[1]);
    }
    
    /** Test que revisa si el request esta dado en el mismo segmento */
    @Test
    public final void testComunDenominador3() {
        final String []s = comunDenominador(
                "/foo/v1/u/juan/entidad-by-cia-tc/814,R", 
                "/bar/v1/u/juan/entidad-by-cia-tc/814,R");
        Assert.assertEquals("/foo/v1/u/juan/entidad-by-cia-tc/814,R", s[0]);
        Assert.assertEquals("/bar/v1/u/juan/entidad-by-cia-tc/814,R", s[1]);
    }
}

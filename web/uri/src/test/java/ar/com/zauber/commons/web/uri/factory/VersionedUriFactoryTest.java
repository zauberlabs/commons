/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
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

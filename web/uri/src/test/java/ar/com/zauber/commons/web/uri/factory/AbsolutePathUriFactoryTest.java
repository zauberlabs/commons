/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.factory;


import org.junit.Assert;
import org.junit.Test;


/**
 * Test de {@link AbsolutePathUriFactory}
 * 
 * @author Mariano Semelman
 * @since Jun 18, 2010
 */
public class AbsolutePathUriFactoryTest {

    /** test de construcción. */
    @Test
    public final void setUp() throws Exception {
        new AbsolutePathUriFactory(new PrefixUriFactory("foo:", 
                new IdentityUriFactory()));
    }
    
    /** */
    @Test
    public final void testAbsolute() throws Exception {
        AbsolutePathUriFactory uf = 
            new AbsolutePathUriFactory(new PrefixUriFactory("foo:", 
                new IdentityUriFactory()));
        Assert.assertEquals("foo:bar", uf.buildUri("bar"));
        Assert.assertEquals("http://bar", uf.buildUri("http://bar"));
    }
}

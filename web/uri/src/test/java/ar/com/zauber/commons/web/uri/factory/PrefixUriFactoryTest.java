/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.factory;


import static junit.framework.Assert.assertEquals;

import org.junit.Test;

/**
 * {@link PrefixUriFactory} Test Case
 * 
 * @author Mariano Cortesi
 * @since May 10, 2010
 */
public class PrefixUriFactoryTest {

    /** test method */
    @Test
    public final void testPrefix() {
        UriFactory uriFactory = new PrefixUriFactory("my-prefix/", 
                new IdentityUriFactory());
        assertEquals("my-prefix/hello", uriFactory.buildUri("hello"));
    }
}

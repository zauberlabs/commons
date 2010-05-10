/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.factory;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;
import org.springframework.mock.web.MockServletContext;


/**
 * {@link ServletPathUriFactory} Test case
 * 
 * @author Mariano Cortesi
 * @since May 10, 2010
 */
public class ServletPathUriFactoryTest {

    /** test case */
    @Test
    public final void testServletPath() {
        MockServletContext servletContext = new MockServletContext();
        servletContext.setContextPath("my-path/");
        ServletPathUriFactory uriFactory = 
            new ServletPathUriFactory(new IdentityUriFactory());
        uriFactory.setServletContext(servletContext);
        
        assertEquals("my-path/hello", uriFactory.buildUri("hello"));
    }
}

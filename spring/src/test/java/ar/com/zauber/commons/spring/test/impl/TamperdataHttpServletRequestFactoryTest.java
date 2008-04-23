/*
 * Copyright (c) 2008 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.spring.test.impl;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;

import ar.com.zauber.commons.spring.test.HttpServletRequestFactory;

/**
 * Test for {@link TamperdataHttpServletRequestFactory}.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Apr 22, 2008
 */
public class TamperdataHttpServletRequestFactoryTest extends TestCase {

    /** testea */
    public final void testFoo() {
        final HttpServletRequestFactory f = 
            new TamperdataHttpServletRequestFactory();
        
        final MockHttpServletRequest request = (MockHttpServletRequest) 
            f.create(getClass().getClassLoader().getResourceAsStream(
                "ar/com/zauber/commons/spring/test/impl/tamperdata-0.xml"));
        
        assertEquals("gzip,deflate", request.getHeader("Accept-Encoding"));
        assertEquals("carlos", request.getParameter("username"));
        assertEquals("POST", request.getMethod());
    }
}

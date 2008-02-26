/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.spring.servlet.mvc.support;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;


/**
 * Test para {@link ZauberBeanNameBasedClassNameHandlerMapping}.
 * 
 * @author Juan F. Codagnone
 * @since Dec 25, 2006
 */
public class ZauberBeanNameBasedClassNameHandlerMappingTest extends TestCase {
    private static final String CONF = "/ar/com/zauber/commons/spring/servlet/mvc/support/map.xml";
    private ConfigurableWebApplicationContext wac;
    private HandlerMapping hm;
    
    /**  @see junit.framework.TestCase#setUp() */
    protected void setUp() throws Exception {
            final MockServletContext sc = new MockServletContext("");
            wac = new XmlWebApplicationContext();
            wac.setServletContext(sc);
            wac.setConfigLocations(new String[] {CONF});
            wac.refresh();
            hm = (HandlerMapping) wac.getBean("handlerMapping");
    }
    
    public void testSimpleName() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest("GET", "/logout/");
        final HandlerExecutionChain hec = hm.getHandler(req);
        assertEquals(((DummyController)hec.getHandler()).getId(), "logout");
    }
    
    public void test404() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest("GET", "/doesntexist/");
        final HandlerExecutionChain hec = hm.getHandler(req);
        assertNull(hec);
    }

    public void testCompound() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest("GET", "/password/change/");
        final HandlerExecutionChain hec = hm.getHandler(req);
        assertEquals(((DummyController)hec.getHandler()).getId(), "changepassword");
    }

    public void testFirstCapitalLetter() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest("GET", "/password/forgot/");
        final HandlerExecutionChain hec = hm.getHandler(req);
        assertEquals(((DummyController)hec.getHandler()).getId(), "forgotpassword");
    }
    
    public void testControllerWithNoName() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest("GET", "/another/");
        final HandlerExecutionChain hec = hm.getHandler(req);
        assertEquals(((DummyController)hec.getHandler()).getId(), "another");
    }

}

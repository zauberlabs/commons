/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.spring.beans.factory.impl;

import javax.servlet.ServletContextEvent;

import junit.framework.TestCase;

import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.WebApplicationContext;

import ar.com.zauber.commons.spring.mock.TestFriendlyContextLoader;
import ar.com.zauber.commons.spring.mock.TestFriendlyContextLoaderListener;

/**
 * TestT
 * 
 * 
 * @author Juan F. Codagnone
 * @since Nov 13, 2007
 */
public class ContextPathRegexCaseBlockTest extends TestCase {

    /** test */
    public final void testCtxHome() {
        final MockServletContext servletCtx = new MockServletContext("/home");
        servletCtx.addInitParameter("contextConfigLocation", 
            "classpath:/spring-test-switch-regex.xml");
        final TestFriendlyContextLoaderListener listener = 
            new TestFriendlyContextLoaderListener();
        listener.contextInitialized(new ServletContextEvent(servletCtx));
        
        final WebApplicationContext ctx = 
            ((TestFriendlyContextLoader)listener.getContextLoader()).getCtx();
        
        assertEquals("home", ctx.getBean("test1"));
    }
    
    /** test */
    public final void testCtxHouse() {
        final MockServletContext servletCtx = new MockServletContext("/house");
        servletCtx.addInitParameter("contextConfigLocation", 
            "classpath:/spring-test-switch-regex.xml");
        final TestFriendlyContextLoaderListener listener = 
            new TestFriendlyContextLoaderListener();
        listener.contextInitialized(new ServletContextEvent(servletCtx));
        
        final WebApplicationContext ctx = 
            ((TestFriendlyContextLoader)listener.getContextLoader()).getCtx();
        
        assertEquals("house", ctx.getBean("test1"));
    }
    
    
    /** test */
    public final void testCtxDefault() {
        final MockServletContext servletCtx = new MockServletContext("/laralara");
        servletCtx.addInitParameter("contextConfigLocation", 
            "classpath:/spring-test-switch-regex.xml");
        final TestFriendlyContextLoaderListener listener = 
            new TestFriendlyContextLoaderListener();
        listener.contextInitialized(new ServletContextEvent(servletCtx));
        
        final WebApplicationContext ctx = 
            ((TestFriendlyContextLoader)listener.getContextLoader()).getCtx();
        
        assertEquals("default", ctx.getBean("test1"));
    }
}

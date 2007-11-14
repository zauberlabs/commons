/*
 * Copyright (c) 2007 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.spring.mock;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import junit.framework.TestCase;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * {@link ContextLoader} subclass that lets {@link TestCase}s to initialize
 * {@link WebApplicationContext} and query for a bean.
 * 
 * For example:
 * <pre>
 * final MockServletContext servletCtx = new MockServletContext("/home");
 * servletCtx.addInitParameter("contextConfigLocation", 
 *                             "classpath:/spring-test-switch-regex.xml");
 * final TestFriendlyContextLoaderListener listener = 
 *          new TestFriendlyContextLoaderListener();
 * listener.contextInitialized(new ServletContextEvent(servletCtx));
 *      
 * final WebApplicationContext ctx = 
 *           ((TestFriendlyContextLoader)listener.getContextLoader()).getCtx();
 *       
 * assertEquals("home", ctx.getBean("test1"));
 * </pre>
 * 
 * @author Juan F. Codagnone
 * @since Nov 13, 2007
 */
public class TestFriendlyContextLoader extends ContextLoader {
    private WebApplicationContext ctx;
    
    /** @see ContextLoader#createWebApplicationContext(ServletContext, 
     *          ApplicationContext) */
    @Override
    protected final WebApplicationContext createWebApplicationContext(
            final ServletContext servletContext, 
            final ApplicationContext parent)
            throws BeansException {
        ctx = super.createWebApplicationContext(servletContext, parent);
        return ctx;
    }

    /**
     * Returns the ctx.
     * 
     * @return <code>WebApplicationContext</code> with the ctx.
     */
    public final WebApplicationContext getCtx() {
        return ctx;
    }
}

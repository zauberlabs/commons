/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

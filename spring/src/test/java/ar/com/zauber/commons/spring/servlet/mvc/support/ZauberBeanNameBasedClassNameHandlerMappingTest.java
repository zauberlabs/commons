/**
 * Copyright (c) 2005-2012 Zauber S.A. <http://www.zaubersoftware.com/>
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
package ar.com.zauber.commons.spring.servlet.mvc.support;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.ParameterizableViewController;


/**
 * Test para {@link ZauberBeanNameBasedClassNameHandlerMapping}.
 * 
 * @author Juan F. Codagnone
 * @since Dec 25, 2006
 */
public class ZauberBeanNameBasedClassNameHandlerMappingTest extends TestCase {
    private static final String CONF = 
        "/ar/com/zauber/commons/spring/servlet/mvc/support/map.xml";
    private ConfigurableWebApplicationContext wac;
    private HandlerMapping hm;
    
    /** @throws Exception on error */
    protected final void setUp() throws Exception {
            final MockServletContext sc = new MockServletContext("");
            wac = new XmlWebApplicationContext();
            wac.setServletContext(sc);
            wac.setConfigLocations(new String[] {CONF});
            wac.refresh();
            hm = (HandlerMapping) wac.getBean("handlerMapping");
    }
    
    /** @throws Exception on error*/
    public final void testSimpleName() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest("GET", "/logout/");
        final HandlerExecutionChain hec = hm.getHandler(req);
        assertEquals(((DummyController)hec.getHandler()).getId(), "logout");
    }
    
    /** @throws Exception on error*/
    public final void test404() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest("GET", 
                "/doesntexist/");
        final HandlerExecutionChain hec = hm.getHandler(req);
        assertNull(hec);
    }

    /** @throws Exception on error*/
    public final void testCompound() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest("GET", 
                "/password/change/");
        final HandlerExecutionChain hec = hm.getHandler(req);
        assertEquals(((DummyController)hec.getHandler()).getId(), "changepassword");
    }

    /** @throws Exception on error*/
    public final void testFirstCapitalLetter() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest("GET", 
                "/password/forgot/");
        final HandlerExecutionChain hec = hm.getHandler(req);
        assertEquals(((DummyController)hec.getHandler()).getId(), "forgotpassword");
    }
    
    /** @throws Exception on error*/
    public final void testControllerWithNoName() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest("GET", "/another/");
        final HandlerExecutionChain hec = hm.getHandler(req);
        assertEquals(((DummyController)hec.getHandler()).getId(), "another");
    }

    /** @throws Exception on error*/
    public final void testParametrizedView() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest("GET", "/oopsss/");
        final HandlerExecutionChain hec = hm.getHandler(req);
        assertNotNull(hec);
        assertEquals(
              ((ParameterizableViewController)hec.getHandler()).getViewName(), 
              "exceptions/http");
    }
    

     /** @throws Exception on error*/
     public final void testActions() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest("GET",
        "/actions/123/delete");
        final HandlerExecutionChain hec = hm.getHandler(req);
        assertEquals("actions", 
                ((DummyController)hec.getHandler()).getId());
        
        
        req = new MockHttpServletRequest("GET",
        "/actions/123");
        final HandlerExecutionChain hec1 = hm.getHandler(req);
        assertEquals("actions", 
                ((DummyController)hec1.getHandler()).getId());
        
        req = new MockHttpServletRequest("GET",
        "/actions/123/cat/321");
        final HandlerExecutionChain hec2 = hm.getHandler(req);
        assertEquals("actions", 
                ((DummyController)hec2.getHandler()).getId());
    }
}

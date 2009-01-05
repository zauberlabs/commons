/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
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
package ar.com.zauber.commons.spring.exceptions;


import java.io.IOException;
import java.security.acl.NotOwnerException;
import java.util.Properties;

import junit.framework.TestCase;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

/**
 * Test for {@link StatusSimpleMappingExceptionHandler}.
 * 
 * @author Juan F. Codagnone
 * @since Aug 3, 2008
 */
public class StatusSimpleMappingExceptionHandlerTest extends TestCase {

    /** test */
    public final void testViewNameNotString() {
        final StatusSimpleMappingExceptionHandler h = 
            new StatusSimpleMappingExceptionHandler();
        final Properties status = new Properties();
        status.put(new Object(), "400");
        try {
            h.setStatusMappings(status);
            fail();
        } catch(IllegalArgumentException e) {
            // ok
        }
    }
    

    /** test */
    public final void testValueMustBeInteger() {
        final StatusSimpleMappingExceptionHandler h = 
            new StatusSimpleMappingExceptionHandler();
        final Properties status = new Properties();
        status.put("test", new Object());
        try {
            h.setStatusMappings(status);
            fail();
        } catch(IllegalArgumentException e) {
            // ok
        }
    }
    
    /** test */
    public final void testFoo() {
        final Properties views = new Properties();
        views.put(NotOwnerException.class.getName(),        "notowner");
        views.put(IllegalArgumentException.class.getName(), "arguments");
        views.put(DataAccessResourceFailureException.class.getName(),  "data");
        
        final Properties status = new Properties();
        status.put("arguments", "400");
        status.put("notowner",  "403");
        status.put("data",      "500");

        final StatusSimpleMappingExceptionHandler h = 
            new StatusSimpleMappingExceptionHandler();
        h.setDefaultStatusCode(200);        
        h.setExceptionMappings(views);
        h.setStatusMappings(status);
        h.setDefaultErrorView("default");
        
        MockHttpServletResponse response; 
        ModelAndView v;
        
        response = new MockHttpServletResponse(); 
        v = h.resolveException(new MockHttpServletRequest(), response, null, 
                new IOException("io!io!"));
        assertEquals("default", v.getViewName());
        assertEquals(200, response.getStatus());
        
        response = new MockHttpServletResponse();
        v = h.resolveException(new MockHttpServletRequest(), response, null, 
                new IllegalArgumentException("asdasd"));
        assertEquals("arguments", v.getViewName());
        assertEquals(400, response.getStatus());
        
        response = new MockHttpServletResponse();
        v = h.resolveException(new MockHttpServletRequest(), response, null, 
                new NotOwnerException());
        assertEquals("notowner", v.getViewName());
        assertEquals(403, response.getStatus());
        
        response = new MockHttpServletResponse(); 
        v = h.resolveException(new MockHttpServletRequest(), response, null, 
                new DataAccessResourceFailureException("bla bla"));
        assertEquals("data", v.getViewName());
        assertEquals(500, response.getStatus());
    }
}

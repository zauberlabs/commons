/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package ar.com.zauber.commons.spring.exceptions;

import java.security.acl.NotOwnerException;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.TestCase;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;

/**
 * Test for {@link HeaderBasedStatusSimpleMappingExceptionHandler}.
 * 
 * @author Francisco J. González Costanzó
 * @since Jul 7, 2010
 */
public class HeaderBasedStatusSimpleMappingExceptionHandlerTest extends
        TestCase {

    /** test */
    public final void testFoo() {
        final Properties views = new Properties();
        views.put(NotOwnerException.class.getName(), "notowner");
        views.put(IllegalArgumentException.class.getName(), "arguments");
        views.put(DataAccessResourceFailureException.class.getName(), "data");

        final Properties status = new Properties();
        status.put("arguments", "400");
        status.put("notowner", "403");
        status.put("data", "500");

        AbstractView view = new AbstractView() {
            @Override
            protected void renderMergedOutputModel(
                    final Map<String, Object> model,
                    final HttpServletRequest request,
                    final HttpServletResponse response)
                    throws Exception {
            }
        };
        view.setBeanName("test");
        final HeaderBasedStatusSimpleMappingExceptionHandler h = 
            new HeaderBasedStatusSimpleMappingExceptionHandler(
                view);
        
        h.setDefaultStatusCode(200);
        h.setExceptionMappings(views);
        h.setStatusMappings(status);
        h.setDefaultErrorView("default");
        
        h.setAccept("application/json");

        MockHttpServletResponse response;
        ModelAndView v;

        response = new MockHttpServletResponse();
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Accept", "application/json");
        
        v = h.resolveException(request, response, null,
                new IllegalArgumentException("io!io!"));
        assertEquals(view, v.getView());
        assertEquals(400, response.getStatus());
    }
}

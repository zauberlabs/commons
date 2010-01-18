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
package ar.com.zauber.commons.spring.web.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

/**
 * Pruebas para {@link ExceptionController}
 * 
 * @author Pablo Grigolatto
 * @since Jun 29, 2009
 */
public class ExceptionControllerTest {
    private ExceptionController exceptionController; 
    private HttpServletResponse res;
    private HttpServletRequest req;

    /** test */
    @Before
    public final void before() {
        res = new MockHttpServletResponse();
        req = new MockHttpServletRequest();
        
        final Map<Integer, String> map = new HashMap<Integer, String>();
            map.put(404, "exceptions/notfound");
            map.put(500, "exceptions/internalerror");
        
        exceptionController = new ExceptionController(map, "exceptions/default");
    }

    /** test */
    @Test
    public final void test404() throws Exception {
        req.setAttribute("javax.servlet.error.status_code", 404);
        final ModelAndView mav = exceptionController.handleRequestInternal(req, res);
        Assert.assertEquals("exceptions/notfound", mav.getViewName());
    }
    
    /** test */
    @Test
    public final void test500() throws Exception {
        req.setAttribute("javax.servlet.error.status_code", 500);
        final ModelAndView mav = exceptionController.handleRequestInternal(req, res);
        Assert.assertEquals("exceptions/internalerror", mav.getViewName());
    }
    
    /** test */
    @Test
    public final void testNull() throws Exception {
        final ModelAndView mav = exceptionController.handleRequestInternal(req, res);
        Assert.assertEquals("exceptions/default", mav.getViewName());
    }
    
}

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
package ar.com.zauber.commons.spring.web.handlers.strategy;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Tests for {@link TransactionStrategy}
 * 
 * @author Pablo Grigolatto
 * @since Dec 2, 2009
 */
public class TransactionStrategyTest {
    private TransactionTemplate defaultTemplate;
    private TransactionTemplate specialTemplate;
    private Controller mockController;
    private Controller anotherMockController;
    
    /** before */
    @Before
    public final void before() {
        defaultTemplate = new TransactionTemplate();
        specialTemplate = new TransactionTemplate();
        specialTemplate.setIsolationLevel(
                TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
        specialTemplate.setPropagationBehavior(
                TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        specialTemplate.setReadOnly(true);
        mockController = new MockController();
        anotherMockController = new AnotherMockController();
    }
    
    /** Test for {@link NullTransactionStrategy} */
    @Test
    public final void testNullStrategy() {
        final TransactionStrategy ts = new NullTransactionStrategy(defaultTemplate);
        final MockHttpServletRequest req = new MockHttpServletRequest();
        final TransactionTemplate txA 
            = ts.getTransactionTemplate(mockController, req);
        final TransactionTemplate txB 
            = ts.getTransactionTemplate(anotherMockController, req);
        
        Assert.assertEquals(defaultTemplate.getIsolationLevel(), 
                            txA.getIsolationLevel());
        Assert.assertEquals(defaultTemplate.getPropagationBehavior(), 
                            txA.getPropagationBehavior());
        Assert.assertEquals(defaultTemplate.getIsolationLevel(), 
                            txB.getIsolationLevel());
        Assert.assertEquals(defaultTemplate.getPropagationBehavior(), 
                            txB.getPropagationBehavior());
    }
    

    /** Test for {@link ByClassTransactionStrategy} */
    @Test
    public final void testStrategyByClass() {
        final Set<Class<?>> specialObjects = new HashSet<Class<?>>();
        specialObjects.add(anotherMockController.getClass());
        final MockHttpServletRequest req = new MockHttpServletRequest();
        
        final TransactionStrategy ts = new ByClassTransactionStrategy(
                defaultTemplate, specialTemplate, specialObjects);
        final TransactionTemplate expectedSpecialTemplate 
            = ts.getTransactionTemplate(anotherMockController, req);
        final TransactionTemplate expectedDefaultTemplate 
            = ts.getTransactionTemplate(mockController, req);
        
        Assert.assertEquals(defaultTemplate.getIsolationLevel(), 
                            expectedDefaultTemplate.getIsolationLevel());
        Assert.assertEquals(defaultTemplate.getPropagationBehavior(), 
                            expectedDefaultTemplate.getPropagationBehavior());
        
        Assert.assertEquals(specialTemplate.getIsolationLevel(), 
                            expectedSpecialTemplate.getIsolationLevel());
        Assert.assertEquals(specialTemplate.getPropagationBehavior(), 
                            expectedSpecialTemplate.getPropagationBehavior());
    }
    
    /** Test for {@link ByClassTransactionStrategy} */
    @Test
    public final void selectStrategyByMethod() {
        final Set<Class<?>> specialObjects = new HashSet<Class<?>>();
        specialObjects.add(anotherMockController.getClass());
        final MockHttpServletRequest request = new MockHttpServletRequest();
        
        final TransactionStrategy ts = new ByMethodTransactionStrategy(
                defaultTemplate, specialTemplate, specialObjects);
        
        request.setMethod("GET");
        Assert.assertEquals(defaultTemplate.getIsolationLevel(), 
              ts.getTransactionTemplate(mockController, request).getIsolationLevel());
        Assert.assertEquals(specialTemplate.getIsolationLevel(), 
                ts.getTransactionTemplate(anotherMockController, request)
                .getIsolationLevel());
        request.setMethod("POST");
        Assert.assertEquals(specialTemplate.getPropagationBehavior(), 
                ts.getTransactionTemplate(mockController, request)
                .getPropagationBehavior());
        request.setMethod("PUT");
        Assert.assertEquals(specialTemplate.getIsolationLevel(), 
                ts.getTransactionTemplate(anotherMockController, request)
                .getIsolationLevel());
    }
    
    /** Mock implementation */
    class MockController implements Controller {
        /** @see Controller#handleRequest(HttpServletRequest, HttpServletResponse) */
        public ModelAndView handleRequest(final HttpServletRequest request,
                final HttpServletResponse response) throws Exception {
            return null;
        }
    }
    /** Another Mock implementation */
    class AnotherMockController implements Controller {
        /** @see Controller#handleRequest(HttpServletRequest, HttpServletResponse) */
        public ModelAndView handleRequest(final HttpServletRequest request,
                final HttpServletResponse response) throws Exception {
            return null;
        }
    }
}

/**
 * Copyright (c) 2005-2015 Zauber S.A. <http://flowics.com/>
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
package ar.com.zauber.spring.taglib;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import ar.com.zauber.commons.dao.closure.ComposeClosure;
import ar.com.zauber.commons.dao.closure.ErrorLoggerWrapperClosure;
import ar.com.zauber.commons.dao.closure.ExecutorClosure;
import ar.com.zauber.commons.dao.closure.FilteredClosure;
import ar.com.zauber.commons.dao.closure.ListClosure;
import ar.com.zauber.commons.dao.closure.MutableClosure;
import ar.com.zauber.commons.dao.closure.NullClosure;
import ar.com.zauber.commons.dao.predicate.AndPredicate;
import ar.com.zauber.commons.dao.predicate.FalsePredicate;
import ar.com.zauber.commons.dao.predicate.NotPredicate;
import ar.com.zauber.commons.dao.predicate.ThrowableMaxAmountPredicate;
import ar.com.zauber.commons.dao.predicate.TruePredicate;

/**
 * TODO Descripcion de la clase. Los comenterios van en castellano.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Aug 20, 2010
 */
@ContextConfiguration(locations="closure-spring.xml")
public class TaglibTest extends AbstractJUnit4SpringContextTests {
    @Resource NullClosure<?> nullClosure;
    @Resource ComposeClosure<?> composeClosure;
    @Resource ListClosure<?> listClosure;
    @Resource ErrorLoggerWrapperClosure<?> errorLoggerWrapperClosure;
    @Resource ExecutorClosure<?> executorClosure;
    @Resource MutableClosure<?> mutableClosure1;
    @Resource MutableClosure<?> mutableClosure2;
    @Resource TruePredicate<?> truePredicate;
    @Resource FalsePredicate<?> falsePredicate;
    @Resource AndPredicate<?> andPredicate;
    @Resource ThrowableMaxAmountPredicate throwsMaxPredicate;
    @Resource NotPredicate<String> notPredicateFoo;
    @Resource FilteredClosure<?> filteredClosure;
    
    @Test
    public void retrieveClosure() throws Exception {
        Assert.assertNotNull(nullClosure);
        Assert.assertNotNull(composeClosure);
        Assert.assertNotNull(listClosure);
        Assert.assertNotNull(executorClosure);
        Assert.assertNotNull(mutableClosure1);
        Assert.assertNull(mutableClosure1.getTarget());
        Assert.assertNotNull(mutableClosure2);
        Assert.assertTrue(mutableClosure2.getTarget() instanceof NullClosure<?>);
        
        Assert.assertNotNull(truePredicate);
        Assert.assertNotNull(falsePredicate);
        Assert.assertNotNull(andPredicate);
        Assert.assertNotNull(throwsMaxPredicate);
        Assert.assertTrue(notPredicateFoo.evaluate("lala"));
        Assert.assertFalse(notPredicateFoo.evaluate("foo"));
        Assert.assertNotNull(filteredClosure);
    }
    
}

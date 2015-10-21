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
package ar.com.zauber.commons.dao.predicate;

import java.util.Collection;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;

import ar.com.zauber.commons.dao.Predicate;


/**
 * Testeo del predicado maxAmountPredicate
 * 
 * @author Mariano Semelman
 * @since Oct 15, 2009
 */
public class ThrowableMaxAmountPredicateTest {
    
    /** test para probar el predicado*/
    @Test
    public final void test() {
        Predicate<Collection<Throwable>> maxAmountPredicate = 
            new ThrowableMaxAmountPredicate(3);
        Collection<Throwable> throwables = new LinkedList<Throwable>();
        for(int x = 0; x <= 3; x++) {
            Assert.assertFalse(maxAmountPredicate.evaluate(throwables));
            throwables.add(new IllegalArgumentException());
        }
        Assert.assertTrue(maxAmountPredicate.evaluate(throwables));
        
       try {
           maxAmountPredicate.evaluate(null);
           Assert.fail();
       } catch(IllegalArgumentException e) {
           //
       }
        
    }

}

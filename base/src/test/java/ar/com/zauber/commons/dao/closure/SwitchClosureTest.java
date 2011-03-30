/**
 * Copyright (c) 2005-2011 Zauber S.A. <http://www.zaubersoftware.com/>
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
package ar.com.zauber.commons.dao.closure;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang.Validate;
import org.junit.Assert;
import org.junit.Test;

import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.commons.dao.Predicate;
import ar.com.zauber.commons.dao.predicate.EqualsPredicate;
import ar.com.zauber.commons.dao.predicate.FalsePredicate;
import ar.com.zauber.commons.dao.predicate.TruePredicate;


/**
 * Tests 
 * 
 * 
 * @author Juan F. Codagnone
 * @since Jan 22, 2010
 */
public class SwitchClosureTest {

    /** test */
    @Test
    public final void testname() throws Exception {
        final List<Entry<Predicate<String>, Closure<String>>> blocks = 
            new LinkedList<Entry<Predicate<String>, Closure<String>>>();
        
        final Predicate<String> never =  new FalsePredicate<String>();
        final Predicate<String> always =  new TruePredicate<String>();
        
        final List<String> l = new LinkedList<String>();
        final Closure<String> putInList = new Closure<String>() {
            public void execute(final String t) {
                l.add(t);
            }
        };
        
        blocks.add(new PredicateClosureEntry<String>(never, 
                new AssertFalseClosure<String>("no debe entrar aca")));
        blocks.add(new PredicateClosureEntry<String>(new EqualsPredicate("foo"), 
                putInList));
        blocks.add(new PredicateClosureEntry<String>(always, 
                new AssertFalseClosure<String>("default block")));
        
        final Closure<String> s = new SwitchClosure<String>(blocks);
        s.execute("foo");
        Assert.assertEquals("foo", l.get(0));
        l.clear();
        try {
            s.execute("bar");
            Assert.fail("shouldnt reach this point");
        } catch(final AssertionError e) {
            Assert.assertEquals("default block", e.getMessage());
        }
        
    }
}

/**
 * closure that fails test
 * 
 * @author Juan F. Codagnone
 * @since Jan 22, 2010
 * @param <T> t
 */
class AssertFalseClosure<T> implements Closure<T> {
    private final String msg;

    /** Creates the AssertFalseClosure. */
    public AssertFalseClosure(final String msg) {
        Validate.notNull(msg);
        this.msg = msg;
    }
    
    /** closure */
    public final void execute(final T t) {
        Assert.fail(msg);
    };
}

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
package ar.com.zauber.commons.dao.impl;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.lang.Validate;
import org.junit.Test;

import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.commons.dao.ClosureProcessor;
import ar.com.zauber.commons.dao.closure.processors.InmutableMergeResult;
import ar.com.zauber.commons.dao.closure.processors.MergeResult;
import ar.com.zauber.commons.dao.closure.processors.OneWayMergeClosureProcessor;
import ar.com.zauber.commons.dao.closure.processors.MergeResult.Operation;


/**
 * Test {@link OneWayMergeClosureProcessor}
 * 
 * @author Juan F. Codagnone
 * @since Jun 19, 2009
 */
public class OneWayMergeClosureProcessorTest {

    /** lo ideal lleno, nada en un lado */
    @Test
    public final void allLeft() {
        final ClosureProcessor<MergeResult<String>> p = 
            new OneWayMergeClosureProcessor<String>(Arrays.asList(
                    new String[]{}), 
                    Arrays.asList(new String[]{"juan", "pedro"}));
        
        final List<MergeResult<String>> expected = 
            new LinkedList<MergeResult<String>>();
        expected.add(new InmutableMergeResult<String>(Operation.ADD, "juan"));
        expected.add(new InmutableMergeResult<String>(Operation.ADD, "pedro"));
        final MergeTestClosure<String> closure = new MergeTestClosure<>(expected.iterator()); 
        p.process(closure);
        closure.assertAll();
    }
    
    /** lo ideal lleno, nada en un lado */
    @Test
    public final void allMergeAdd() {
        final ClosureProcessor<MergeResult<String>> p = 
            new OneWayMergeClosureProcessor<String>(Arrays.asList(
                    new String[]{"juan", "pedro"}), 
                    Arrays.asList(new String[]{"juan", "jose", "pedro", }));
        
        final List<MergeResult<String>> expected = 
            new LinkedList<MergeResult<String>>();
        expected.add(new InmutableMergeResult<String>(Operation.KEEP, "juan"));
        expected.add(new InmutableMergeResult<String>(Operation.ADD, "jose"));
        expected.add(new InmutableMergeResult<String>(Operation.KEEP, "pedro"));
        final MergeTestClosure<String> closure = new MergeTestClosure<>(expected.iterator()); 
        p.process(closure);
        closure.assertAll();
    }
    
    /** lo ideal lleno, nada en un lado */
    @Test
    public final void allMergeRemove() {
        final ClosureProcessor<MergeResult<String>> p = 
            new OneWayMergeClosureProcessor<String>(Arrays.asList(
                    new String[]{"juan", "jose", "pedro"}), 
                    Arrays.asList(new String[]{"juan", "pedro", }));
        
        final List<MergeResult<String>> expected = 
            new LinkedList<MergeResult<String>>();
        expected.add(new InmutableMergeResult<String>(Operation.KEEP, "juan"));
        expected.add(new InmutableMergeResult<String>(Operation.REMOVE, "jose"));
        expected.add(new InmutableMergeResult<String>(Operation.KEEP, "pedro"));
        final MergeTestClosure<String> closure = new MergeTestClosure<>(expected.iterator()); 
        p.process(closure);
        closure.assertAll();
    }
    
    /** lo ideal lleno, nada en un lado */
    @Test
    public final void allRemove() {
        final ClosureProcessor<MergeResult<String>> p = 
            new OneWayMergeClosureProcessor<String>(Arrays.asList(
                    new String[]{"juan", "jose", "pedro"}), 
                    Arrays.asList(new String[]{}));
        
        final List<MergeResult<String>> expected = 
            new LinkedList<MergeResult<String>>();
        expected.add(new InmutableMergeResult<String>(Operation.REMOVE, "juan"));
        expected.add(new InmutableMergeResult<String>(Operation.REMOVE, "jose"));
        expected.add(new InmutableMergeResult<String>(Operation.REMOVE, "pedro"));
        final MergeTestClosure<String> closure = new MergeTestClosure<>(expected.iterator()); 
        p.process(closure);
        closure.assertAll();
    }
    
    /** lo ideal lleno, nada en un lado */
    @Test
    public final void sinCompares() {
        final ClosureProcessor<MergeResult<A>> p = 
            new OneWayMergeClosureProcessor<A>(
                Arrays.asList(new A[]{new A(0), new A(1), new A(2)}), 
                Arrays.asList(new A[]{new A(1)}),
                (a,b) -> Integer.compare(a.getA(), b.getA()));
        
        final List<MergeResult<A>> expected = new LinkedList<MergeResult<A>>();
        expected.add(new InmutableMergeResult<A>(Operation.REMOVE, new A(0)));
        expected.add(new InmutableMergeResult<A>(Operation.KEEP, new A(1)));
        expected.add(new InmutableMergeResult<A>(Operation.REMOVE, new A(2)));
        final MergeTestClosure<A> closure = new MergeTestClosure<>(expected.iterator()); 
        p.process(closure);
        closure.assertAll();
    }
}

/**
 * Prueba que un closure recibe lo que debe recibir 
 * @author Juan F. Codagnone
 * @since Jun 19, 2009
 */
class MergeTestClosure<T> implements Closure<MergeResult<T>> {
    private final Iterator<MergeResult<T>> expected;
    private MergeResult<T> current;
    
    
    /** Creates the TestClosure. */
    public MergeTestClosure(final Iterator<MergeResult<T>> expected) {
        Validate.notNull(expected);
        
        if(expected.hasNext()) {
            current = expected.next();
        }
        this.expected = expected;
    }
    
    /** @see Closure#execute(Object) */
    public void execute(final MergeResult<T> result) {
        Assert.assertTrue(current != null);
        Assert.assertTrue(result != null);
        
        Assert.assertEquals(current.getOperation(), result.getOperation());
        Assert.assertEquals(current.getEntity(), result.getEntity());
        
        if(expected.hasNext()) {
            current = expected.next();
        }
    }
    
    /** se procesaron todas las cosas que se esperaba? */
    public void assertAll() {
        Assert.assertTrue(!expected.hasNext());
    }
    
}

class A {
    private final int a;
    
    A(final int a) {
        this.a = a;
    }

    public int getA() {
        return a;
    }
    
    @Override
    public boolean equals(Object obj) {
        boolean ret = false;
        if(obj == this) {
            ret = true;
        } else if(obj instanceof A) {
            ret = a == ((A)obj).a;
        }
        return ret;
    }
 }
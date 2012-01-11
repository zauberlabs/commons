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
package ar.com.zauber.commons.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ar.com.zauber.commons.dao.Predicate;
import ar.com.zauber.commons.dao.predicate.FalsePredicate;
import ar.com.zauber.commons.dao.predicate.TruePredicate;


/**
 * test {@link PredicateIterator}.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Jun 16, 2009
 */
public class PredicateIteratorTest {

    /** prueba el filtrado de todo vacio */
    @Test
    public final void allFalse() {
        final Iterator<String> iterator = new PredicateIterator<String>(
                Arrays.asList(new String[]{"hola", "chau"}).iterator(),
                new FalsePredicate<String>());
        
        Assert.assertFalse(iterator.hasNext());
    }
    
    /** prueba el filtrado de uno y uno */
    @Test
    public final void unoYUno() {
        final Iterator<String> iterator = new PredicateIterator<String>(
                Arrays.asList(new String[]{"1", "2", "3", "4", "5"}).iterator(),
                new Predicate<String>() {
                    private int i = 1;
                    public boolean evaluate(final String value) {
                        return (i++ % 2 == 0);
                    }
                });
        
        final List<String> ret = new ArrayList<String>();
        while(iterator.hasNext()) {
            ret.add(iterator.next());
        }
        Assert.assertArrayEquals(ret.toArray(), new Object[]{"2", "4"});
    }
    
    /** prueba el filtrado sin filtrado... */
    @Test
    public final void allTrue() {
        final Iterator<String> iterator = new PredicateIterator<String>(
                Arrays.asList(new String[]{"hola", "chau"}).iterator(),
                new TruePredicate<String>());
        
        final List<String> ret = new ArrayList<String>();
        while(iterator.hasNext()) {
            ret.add(iterator.next());
        }
        Assert.assertArrayEquals(ret.toArray(), new Object[]{"hola", "chau"});
    }
    
}

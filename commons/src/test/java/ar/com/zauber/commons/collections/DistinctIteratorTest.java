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

package ar.com.zauber.commons.collections;


import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;


/**
 * Prueba de {@link DistinctIterator}.
 * 
 * @author Juan Almeyda
 * @since Sep 7, 2009
 */
public class DistinctIteratorTest {
    private Comparator<String> comparator = new Comparator<String>() {
        public int compare(final String o1, final String o2) {
            return o1.compareTo(o2);
        }
    };
    
    /** prueba muchas cadenas seguidas*/
    @Test
    public final void testUnique() throws Exception {
        final Iterator<String> it =  new DistinctIterator<String>(Arrays.asList(
                "hola", "hola", "hola", "chau", "chau", "chau").iterator(),
                comparator);
        
        Assert.assertEquals("hola", it.next());
        Assert.assertEquals("chau", it.next());
        Assert.assertFalse(it.hasNext());
    }
    
    /** prueba ningun elemento*/
    @Test
    public final void testVacia() {
        final Iterator<String> it =  new DistinctIterator<String>(Arrays.asList(
                new String[] {}
                ).iterator(), comparator);
        
        Assert.assertFalse(it.hasNext());
    }
    
    /** prueba un solo elemento */
    @Test
    public final void testUnico() {
        final Iterator<String> it =  new DistinctIterator<String>(Arrays.asList(
                "hola").iterator(), comparator);
        Assert.assertEquals("hola", it.next());
        Assert.assertFalse(it.hasNext());
    }
    
    /** prueba de solo 2 elementos */
    @Test
    public final void testTwoElementos() {
        final Iterator<String> it =  new DistinctIterator<String>(Arrays.asList(
                "hola", "chau").iterator(), comparator);
        Assert.assertEquals("hola", it.next());
        Assert.assertEquals("chau", it.next());
        Assert.assertFalse(it.hasNext());
    }
    
    /** prueba de 2 elementos iguales */
    @Test
    public final void test2Iguales() {
        final Iterator<String> it =  new DistinctIterator<String>(Arrays.asList(
                "hola", "hola").iterator(), comparator);
        Assert.assertEquals("hola", it.next());
        Assert.assertFalse(it.hasNext());
    }
}

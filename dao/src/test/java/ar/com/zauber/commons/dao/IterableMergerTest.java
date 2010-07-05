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
package ar.com.zauber.commons.dao;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests para {@link IterableMerger}
 * 
 * @author Pablo Martin Grigolatto
 * @since Jul 2, 2010
 */
public class IterableMergerTest {

    private static final int CENTER = 50;

    /** merge */
    @Test
    public final void testMerge() {
        final List<Integer> ret = new LinkedList<Integer>();
        Closure<Integer> target = new Closure<Integer>() {
            public void execute(final Integer t) {
                ret.add(t);
            }
        };
        Comparator<Integer> comparator = new Comparator<Integer>() {
            public int compare(final Integer o1, final Integer o2) {
                Integer d1 = Math.abs(CENTER - o1);
                Integer d2 = Math.abs(CENTER - o2);
                return d1.compareTo(d2);
            }
        };

        /*
         * distancias     01,14,17,29,29,35 
         * orden esperado 52,64,33,79,21,15
         */
        Integer n79 = 79;
        Integer n21 = 21;
        Iterable<Integer> iterableLeft = Arrays.asList(52, 64, n79);  // 02,14,29
        Iterable<Integer> iterableRight = Arrays.asList(33, n21, 15); // 17,29,35

        new IterableMerger<Integer>(comparator, target)
            .merge(iterableLeft, iterableRight);

        Assert.assertArrayEquals(
                Arrays.asList(52, 64, 33, 79, 21, 15).toArray(),
                ret.toArray());
        
        //prioridad al primer iterable
        Assert.assertTrue(ret.get(3) == n79);
        Assert.assertTrue(ret.get(4) == n21);
    }

}

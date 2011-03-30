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
package ar.com.zauber.commons.collections;

import java.util.Comparator;
import java.util.Iterator;

import junit.framework.TestCase;


/**
 * Test for {@link DynamicSortedCollectionTest} 
 * 
 * @author Christian Nardi
 * @since May 15, 2009
 */
public class DynamicSortedCollectionTest extends TestCase {

    /**
     * @throws Exception
     */
    public final void testDynamicSortedCollection() throws Exception {
        final DynamicSortedCollection<String> collection = 
            new DynamicSortedCollection<String>(new AscComparator());
        final String[] strings = {"A string", "But not the only one", 
                "Cause there are others", "Waiting, waiting and waiting"};
        
        collection.add(strings[1]);        
        collection.add(strings[3]);
        collection.add(strings[0]);
        collection.add(strings[2]);
        
        Iterator<String> it = collection.iterator();
        
        for (int i = 0; i < strings.length; i++) {
            assertEquals(strings[i], it.next());
        }
        
        collection.setComparator(new DescComparator());
        
        it = collection.iterator();
        for (int i = strings.length - 1; i >= 0; i--) {
            assertEquals(strings[i], it.next());
        }
    }
    
    /**
     * Ascending comparator 
     * 
     * @author Christian Nardi
     * @since May 15, 2009
     */
    class AscComparator implements Comparator<String> {
        /** @see Comparator#compare(java.lang.Object, java.lang.Object) */
        public int compare(final String o1, final String o2) {
            return o1.compareTo(o2);
        }
    }
    
    /**
     * Descending comparator 
     * 
     * @author Christian Nardi
     * @since May 15, 2009
     */
    class DescComparator implements Comparator<String> {
        /** @see Comparator#compare(java.lang.Object, java.lang.Object) */
        public int compare(final String o1, final String o2) {
            return o2.compareTo(o1);
        }
    }
}

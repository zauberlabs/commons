/*
 * Copyright (c) 2009 Gire S.A.  -- All rights reserved
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

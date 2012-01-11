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
package ar.com.zauber.commons.collections;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;


/**
 * Tests {@link OnModificationProxyCollection}.
 * 
 * @author Juan Almeyda
 * @since Aug 5, 2009
 */
public class OnModificationProxyCollectionTest {

    /** tests equals */
    @Test
    public final void testEquals() {
        final List<String> target = Arrays.asList(new String[] {
                "hello", "good bye"
        });
        final Collection<String> c1 = new OnModificationProxyCollection<String>() {
            @Override
            protected Collection<String> getTarget() {
                return target;
            }
        };
        final Collection<String> c2 = new OnModificationProxyCollection<String>() {
            @Override
            protected Collection<String> getTarget() {
                return target;
            }
        };
        
        Assert.assertTrue(c1.equals(c2));
    }
    
    /** tests equals */
    @Test
    public final void testNotEquals() {
        final Collection<String> c1 = new OnModificationProxyCollection<String>() {
            private final List<String> target = Arrays.asList(new String[] {
                    "hello", "good bye"
            });
            @Override
            protected Collection<String> getTarget() {
                return target;
            }
        };
        final Collection<String> c2 = new OnModificationProxyCollection<String>() {
            private final List<String> target = Arrays.asList(new String[] {
                    "hola", "chau"
            });

            @Override
            protected Collection<String> getTarget() {
                return target;
            }
        };
        
        Assert.assertFalse(c1.equals(c2));
    }
    
    /** test iterator */
    @Test
    public final void testIterator() {
        final Collection<String> c1 = new OnModificationProxyCollection<String>() {
            private final List<String> target = new LinkedList<String>(
                    Arrays.asList(new String[] {
                    "hello", "good bye", "hi", "snake"
            }));
            @Override
            protected Collection<String> getTarget() {
                return target;
            }
            /** @see OnModificationProxyCollection#onRemovePre(Object) */
            @Override
            protected void onRemovePre(final String o) {
                Assert.assertEquals("hi", o);
            }
            /** @see OnModificationProxyCollection#onRemovePost(Object) */
            @Override
            protected void onRemovePost(final String o) {
                Assert.assertEquals("hi", o);
            }
        };
        
        final Iterator<String> it = c1.iterator();
        it.next(); //hello
        it.next(); //good bye
        it.next(); //hi
        it.remove();
        it.next().equals("snake");
    }
    
    /** test clear */
    @Test
    public final void testClear() {
        final Collection<String> c1 = new OnModificationProxyCollection<String>() {
            private final List<String> target = new LinkedList<String>(
                    Arrays.asList(new String[] {
                    "hello", "good bye", "hi", "snake"
            }));
            private String last;
            @Override
            protected Collection<String> getTarget() {
                return target;
            }
            /** @see OnModificationProxyCollection#onRemovePre(Object) */
            @Override
            protected void onRemovePre(final String o) {
                last = o;
            }
            /** @see OnModificationProxyCollection#onRemovePost(Object) */
            @Override
            protected void onRemovePost(final String o) {
                Assert.assertEquals(last, o);
            }
        };
        
        c1.clear();
        Assert.assertTrue(c1.isEmpty());
    }
}

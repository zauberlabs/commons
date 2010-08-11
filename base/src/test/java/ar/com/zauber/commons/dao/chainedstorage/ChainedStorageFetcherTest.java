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
package ar.com.zauber.commons.dao.chainedstorage;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.eq;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ar.com.zauber.commons.dao.chainedstorage.impl.MapBasedChainedStorageFetcher;

/**
 * Pruebas sobre {@link ChainedStorageFetcher} 
 * 
 * @author Pablo Grigolatto, Marcelo Turrin
 * @since Jul 30, 2010
 */
public class ChainedStorageFetcherTest {
    private static final String KEY_1 = "key1";
    private static final String VALUE_1 = "value1";
    private static final String KEY_2 = "key2";
    private static final String VALUE_2 = "value2";

    private Map<String, String> initialMap;

    /** before */
    @Before
    public final void before() {
        initialMap = new HashMap<String, String>();
        initialMap.put(KEY_1, VALUE_1);
    }

    /** el {@link ChainedStorageFetcher} contiene la key solicitada */
    @Test
    public final void testStorageDoesContainsKey() {
        // fixture
        final ChainedStorageFetcher<String, String> child 
            = mock(ChainedStorageFetcher.class);
        
        final Storage<String, String> parentStorage = mock(Storage.class);
        doAnswer(new Answer<String>() {
            public String answer(final InvocationOnMock invocation)
                    throws Throwable {
                String rta = (String) invocation.getArguments()[1];
                Assert.assertEquals(VALUE_1, rta);
                return null;
            }
        }).when(parentStorage).store(matches(KEY_1), anyString());

        final ChainedStorageFetcher fetcher 
            = new MapBasedChainedStorageFetcher<String, String>(
                    child, initialMap);

        // work
        fetcher.fetch(KEY_1, parentStorage);

        // assert
        verify(child, never()).fetch(anyString(), any(Storage.class));
    }

    /** el {@link ChainedStorageFetcher} no contiene la key solicitada
     *  y debe delegar a su hijo */
    @Test
    public final void testStorageDoesNotContainsKey() {
        // fixture
        final Storage<String, String> parentStorage = mock(Storage.class);

        final ChainedStorageFetcher<String, String> child 
            = mock(ChainedStorageFetcher.class);
        
        final ChainedStorageFetcher fetcher 
            = new MapBasedChainedStorageFetcher<String, String>(
                    child, initialMap);
        
        doAnswer(new Answer<String>() {
            public String answer(final InvocationOnMock invocation)
                    throws Throwable {
          
                //recibe los parametros
                final String key = (String) invocation.getArguments()[0];
                final Storage<String, String> parent 
                    = (Storage<String, String>) invocation.getArguments()[1];
                
                //llama al padre con el valor correspondiente a esa key
                if(KEY_2.equals(key)) {
                    parent.store(key, VALUE_2);
                }
                return null;
            }
        }).when(child).fetch(matches(KEY_2), eq(fetcher));

        // work
        fetcher.fetch(KEY_2, parentStorage);

        // assert
        verify(child, times(1)).fetch(matches(KEY_2), eq(fetcher));
        Assert.assertEquals(VALUE_2, initialMap.get(KEY_2));
        verify(parentStorage, times(1)).store(matches(KEY_2), matches(VALUE_2));
    }

}

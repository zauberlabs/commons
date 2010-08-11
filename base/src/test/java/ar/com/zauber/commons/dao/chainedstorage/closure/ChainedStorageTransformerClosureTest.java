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
package ar.com.zauber.commons.dao.chainedstorage.closure;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.commons.dao.chainedstorage.ChainedStorageFetcher;
import ar.com.zauber.commons.dao.chainedstorage.Storage;

/**
 * Pruebas sobre {@link ChainedStorageTransformerClosure}
 * 
 * @author Pablo Grigolatto, Marcelo Turrin
 * @since Jul 2, 2010
 */
public class ChainedStorageTransformerClosureTest {
    private static final String KEY = "key";
    private static final String VALUE = "value";

    /** Testea que el encadenamiento del {@link Closure} */
    @Test
    public final void testClosureChain() {

        // mock fetcher
        ChainedStorageFetcher<String, String> fetcher 
            = mock(ChainedStorageFetcher.class);
        
        doAnswer(new Answer<String>() {
            /** @see Answer#answer(org.invocation.InvocationOnMock) */
            public String answer(final InvocationOnMock invocation) 
                    throws Throwable {
                String k = (String) invocation.getArguments()[0];
                Storage<String, String> parent 
                    = (Storage<String, String>) invocation.getArguments()[1];
                if (KEY.equals(k)) {
                    parent.store(KEY, VALUE);
                }
                return VALUE;
            }
        }).when(fetcher).fetch(
                anyString(), any(ChainedStorageFetcher.class));


        //mock closure
        Closure<String> targetClosure = mock(Closure.class);
        ChainedStorageTransformerClosure<String, String> cstc 
            = new ChainedStorageTransformerClosure<String, String>(
                    fetcher, targetClosure);

        //Comportamiento
        //No encuentra
        cstc.execute("NADA");
        verify(targetClosure, never()).execute(anyString());
        
        cstc.execute("Fruta");
        verify(targetClosure, never()).execute(anyString());
        
        //Se ejecuta
        cstc.execute(KEY);
        //Debería haberse llamado con la respuesta
        verify(targetClosure).execute(matches(VALUE));
    }
}

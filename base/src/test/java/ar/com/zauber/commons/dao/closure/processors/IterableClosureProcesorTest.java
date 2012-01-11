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
package ar.com.zauber.commons.dao.closure.processors;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ar.com.zauber.commons.dao.closure.ListClosure;


/**
 * Tests for {@link IterableClosureProcesor}.
 * 
 * @author Matías G. Tito
 * @since Sep 29, 2009
 */
public class IterableClosureProcesorTest {

    @Test
    public void foo()  {
        final List<String> personas = Arrays.asList("juan", "matias");
        final ListClosure<String> result = new ListClosure<String>();
        new IterableClosureProcesor<String>(personas).process(result);
        Assert.assertEquals(personas, result.getList());
    }
}

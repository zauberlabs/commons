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
package ar.com.zauber.commons.dao.exception;

import java.util.Collection;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;


/**
 * TODO Descripcion de la clase. Los comentarios van en castellano.
 * 
 * 
 * @author Mariano Semelman
 * @since Oct 16, 2009
 */
public class TriggeredExceptionTest {

    /** test */
    @Test 
    public final void test() {
        Throwable exc = new TriggeredException();
        Assert.assertTrue(exc.toString().contains("TriggeredException"));
        Collection<Throwable> throwables = new LinkedList<Throwable>();
        throwables.add(new IllegalArgumentException());
        exc = new TriggeredException(throwables);
        boolean todos = true;
        boolean entro = false;
        for(Throwable t : throwables) {
            todos = todos && exc.toString().contains(t.toString());
            entro = true;
        }
        Assert.assertTrue(todos);
        Assert.assertTrue(entro);
    }
}

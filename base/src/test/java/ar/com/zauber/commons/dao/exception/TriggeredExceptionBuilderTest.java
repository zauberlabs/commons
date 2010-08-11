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
package ar.com.zauber.commons.dao.exception;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import ar.com.zauber.commons.dao.Predicate;
import ar.com.zauber.commons.dao.exception.TriggeredException;
import ar.com.zauber.commons.dao.exception.TriggeredExceptionBuilder;
import ar.com.zauber.commons.dao.predicate.ThrowableMaxAmountPredicate;


/**
 * TODO Descripcion de la clase. Los comentarios van en castellano.
 * 
 * @author Mariano Semelman
 * @since Oct 15, 2009
 */
public class TriggeredExceptionBuilderTest {

    /** test general */
    @Test
    public final void test() {
        Predicate<Collection<Throwable>> condition = 
            new ThrowableMaxAmountPredicate(3);
        TriggeredExceptionBuilder builder = new TriggeredExceptionBuilder(condition);
        for(int x = 0; x < 3; x++) {
            builder.add(new IllegalArgumentException());
        }
        try {
            builder.add(new IllegalArgumentException());
            Assert.fail();
        } catch(TriggeredException e) {
            Assert.assertEquals(4, builder.getThrowablesOcurred().size());
        }
    }
}

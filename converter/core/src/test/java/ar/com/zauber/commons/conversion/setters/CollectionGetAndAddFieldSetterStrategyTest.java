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
package ar.com.zauber.commons.conversion.setters;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.BeanWrapperImpl;

import ar.com.zauber.commons.conversion.Foo;


/**
 * Tests {@link CollectionGetAndAddFieldSetterStrategy}
 * 
 * @author Juan F. Codagnone
 * @since Sep 2, 2010
 */
public class CollectionGetAndAddFieldSetterStrategyTest {
    
    /** prueba simple */
    @Test
    public final void correctcase() {
        final Foo foo = new Foo("hola");
        Assert.assertTrue(foo.getEntradas().isEmpty());
        FieldSetterStrategies.COLLECTION_ADD_STRATEGY.setProperty(
                new BeanWrapperImpl(foo), "entradas", 
                Arrays.asList("hola", "mundo"));
        final Iterator<String> it = foo.getEntradas().iterator();
        Assert.assertEquals("hola", it.next());
        Assert.assertEquals("mundo", it.next());
        Assert.assertFalse(it.hasNext());
    }
    
    /** target no es collection  */
    @Test(expected = IllegalStateException.class)
    public final void incorrectTargetCase() {
        final Foo foo = new Foo("hola");
        Assert.assertTrue(foo.getEntradas().isEmpty());
        FieldSetterStrategies.COLLECTION_ADD_STRATEGY.setProperty(
                new BeanWrapperImpl(foo), "bar", 
                Arrays.asList("hola", "mundo"));
    }
    
    /** source no es iterable */
    @Test(expected = IllegalStateException.class)
    public final void incorrectSourceCase() {
        final Foo foo = new Foo("hola");
        Assert.assertTrue(foo.getEntradas().isEmpty());
        FieldSetterStrategies.COLLECTION_ADD_STRATEGY.setProperty(
                new BeanWrapperImpl(foo), "entradas", 
                "probando");
    }
}

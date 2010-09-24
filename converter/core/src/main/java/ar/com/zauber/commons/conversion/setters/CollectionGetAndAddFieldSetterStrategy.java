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
package ar.com.zauber.commons.conversion.setters;

import java.util.Collection;

import org.springframework.beans.BeanWrapper;

import ar.com.zauber.commons.conversion.FieldSetterStrategy;

/**
 * {@link FieldSetterStrategies} para aquellas propiedades que son colecciones
 * y que no tienen setters. Se llama al get de la coleccion y se hace el add. 
 * 
 * @author Juan F. Codagnone
 * @since Sep 2, 2010
 */
public class CollectionGetAndAddFieldSetterStrategy implements FieldSetterStrategy {

    /** @see FieldSetterStrategy#setProperty(BeanWrapper, String, Object) */
    public final void setProperty(final BeanWrapper bean, final String targetName, 
            final Object value) {
        if(!(value instanceof Iterable<?>)) {
            throw new IllegalStateException("The source object must be iterable."
                    + value.getClass().getCanonicalName() + " given.");
        }
        final Iterable<?> sourceCollection  = (Iterable<?>) value;
        final Object t = bean.getPropertyValue(targetName);
        if(!(t instanceof Collection<?>)) {
            throw new IllegalStateException(
                    "The target object must be a collection. "
                    + t.getClass().getCanonicalName() + " given.");
        }        
        final Collection<Object> targetCollection = (Collection<Object>) t; 
        for(final Object o : sourceCollection) {
            targetCollection.add(o);
        }
    }
}

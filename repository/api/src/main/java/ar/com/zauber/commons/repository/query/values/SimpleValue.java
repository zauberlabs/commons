/**
 * Copyright (c) 2005-2015 Zauber S.A. <http://flowics.com/>
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
package ar.com.zauber.commons.repository.query.values;
        

/**
 * Valor simple donde el mismo representa un parametro que se usa directamente
 * dentro del filtro (a diferencia de un valor complejo donde puede haber más
 * procesamiento y traducción.
 * 
 * @author Martin A. Marquez
 * @since Sep 24, 2007
 */
public class SimpleValue implements Value {
    private final Object value;

    /** @param value actual value*/
    public SimpleValue(final Object value) {
        this.value = value;
    }

    /** @return the value*/
    public final Object getValue() {
        return value;
    }
    
}

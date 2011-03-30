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
package ar.com.zauber.commons.conversion;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;

/**
 * 
 * Contextual information about the conversion. When a conversion depends on
 * extra information, the <code>ConversionContext</code> is user to contain that
 * information and propagate it through the internal converters. Each converter
 * implementation can get this information from the <code>ConversionContext</code>
 * and use it as parameters for the source object's methods.
 *
 * 
 * @author Mariano Cortesi
 * @since Nov 5, 2009
 */
public class ConversionContext {

    private Map<String, Object> attributes = new HashMap<String, Object>();
    
    /**
     * Creates the ConversionContext.
     */
    public ConversionContext() {
    }
    
    /**
     * Crea un contexto con un parametro y su valor
     */
    public ConversionContext(final String key, final Object value) {
        Validate.notNull(key);
        Validate.notNull(value);
        this.set(key, value);
    }

    /**
     * Setea un parametro en el contexto de conversion
     * 
     * @param key nombre del parametro
     * @param value valor
     */
    public final void set(final String key, final Object value) {
        Validate.notNull(key);
        Validate.notNull(value);
        this.attributes.put(key, value);
    }
    
    /**
     * Retorna el valor de un parametro
     * 
     * @param key nombre del parametro
     * @return valor del parametro
     */
    public final Object get(final String key) {
        return this.attributes.get(key);
    }
    
    /**
     * Indica si existe una variable en el contexto
     * 
     * @param key nombre del parametro
     */
    public final boolean has(final String key) {
        return this.attributes.containsKey(key);
    }
}

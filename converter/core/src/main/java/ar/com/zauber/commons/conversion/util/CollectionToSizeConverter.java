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
package ar.com.zauber.commons.conversion.util;

import java.util.Collection;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.conversion.ConversionContext;
import ar.com.zauber.commons.conversion.Converter;


/**
 * Converter que convierte una <code>Collection</code> en su tamaño
 * 
 * @author Juan Edi
 * @author Mariano A. Cortesi
 * @since Nov 3, 2009
 */
public class CollectionToSizeConverter implements Converter<Collection<?>, Integer> {

    /** @see Converter#convert(Object, ConversionContext) */
    public final Integer convert(final Collection<?> source,
            final ConversionContext ctx) {
        Validate.notNull(source);
        return source.size();
    }

}

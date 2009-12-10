/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
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

import java.util.List;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.conversion.ConversionContext;
import ar.com.zauber.commons.conversion.Converter;


/**
 * Converts a non-empty <code>List</code> to its first element. 
 * 
 * @param <T> Type of the elements of the <code>List</code>
 * @author Juan Edi
 * @since Nov 9, 2009
 */
public class FirstElementConverter <T>
                implements Converter<List<T>, T> {

    /** @Converter#convert(java.lang.Object, ConversionContext) */
    public final T convert(final List<T> source, final ConversionContext ctx) {
        Validate.notNull(source);
        Validate.notEmpty(source);
        return source.get(0);
    }

}

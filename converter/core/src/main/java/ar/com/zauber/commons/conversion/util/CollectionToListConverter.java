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
package ar.com.zauber.commons.conversion.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.conversion.ConversionContext;
import ar.com.zauber.commons.conversion.Converter;


/**
 * Converts a <code>Collection</code> to a <code>List</code> of elements of
 * other type, applying an defined <code>Converter</code> for the elements. 
 * 
 * @param <S>
 *            Type of the elements of the source <code>Collection</code>
 * @param <T>
 *            Type of the elements of the target <code>List</code>
 * 
 * @author Juan Edi
 * @since Nov 4, 2009
 */
public class CollectionToListConverter<S, T>
        implements Converter<Collection<S>, List<T>> {

    private Converter<S, T> elementConverter;

    /**
     * Creates the ListConverter.
     * 
     * @param elementConverter
     *            <code>Converter</code> to use for each element. 
     */
    public CollectionToListConverter(final Converter<S, T> elementConverter) {
        Validate.notNull(elementConverter);
        this.elementConverter = elementConverter;
    }

    /** @see Converter#convert(Object, ConversionContext) */
    public final List<T> convert(final Collection<S> source,
                                final ConversionContext ctx) {
        Validate.notNull(source);
        List<T> targetList = new LinkedList<T>();
        
        for (S s : source) {
            targetList.add(elementConverter.convert(s, ctx));
        }
        
        return targetList;
    }

}

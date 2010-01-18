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

import ar.com.zauber.commons.conversion.Converter;

/**
 * Converts a property from a source object to one of the target object.
 * 
 * @param <S> source type of the conversion.
 * @param <SF> type of the property to be extracted from de source object.
 * @param <T> target type of the counversion.
 * @author Juan Edi
 * @since Nov 16, 2009
 */
@SuppressWarnings("unused")
public class SinglePropertyConverter<S, SF, T>
                                        extends CompositeConverter<S, SF, T> {

    private final String sourcePropertyName;
    private final Converter<SF, T> converter;
    
    
    /**
     * Creates the CompositeSinglePropertyConverter.
     *
     * @param converterA
     * @param converterB
     * @param sourcePropertyName
     * @param converter
     */
    public SinglePropertyConverter(final String sourcePropertyName,
                                    final Converter<SF, T> converter) {
        super(new PropertyExtractorConverter<S, SF>(sourcePropertyName),
                converter, false);
        this.sourcePropertyName = sourcePropertyName;
        this.converter = converter;
    }
    

    
}
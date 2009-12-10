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

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.conversion.ConversionContext;
import ar.com.zauber.commons.conversion.Converter;

/**
 * Composes two conversions, based on two internal converters. It is constructed
 * with a boolean value that tells whether or not to propagate <code>null</code>
 * values in the conversion. If set to <code>true</code>, a <code>null</code>
 * result from the first conversion will be used as parameter for the second.
 * Otherwise, <code>null</code> is returned directly.
 * 
 * 
 * @param <S> source type of the conversion.
 * @param <U> intermediate type of the conversion.
 * @param <T> target type of the conversion.
 * @author Juan Edi
 * @since Nov 16, 2009
 */
public class CompositeConverter<S, U, T> implements Converter<S, T> {

    private final Converter<S, U> converterA;
    private final Converter<U, T> converterB;
    private final boolean propagateNull;
    
    
    /**
     * Creates the CompositeConverter.
     *
     * @param converterA
     * @param converterB
     */
    public CompositeConverter(final Converter<S, U> converterA,
            final Converter<U, T> converterB) {
        this(converterA, converterB, false);
    }


    /**
     * Creates the CompositeConverter.
     *
     * @param converterA
     * @param converterB
     * @param propagateNull
     */
    public CompositeConverter(final Converter<S, U> converterA,
            final Converter<U, T> converterB, final boolean propagateNull) {
        super();
        Validate.notNull(converterA);
        Validate.notNull(converterB);
        this.converterA = converterA;
        this.converterB = converterB;
        this.propagateNull = propagateNull;
    }



    /** @see Converter#convert(java.lang.Object, ConversionContext) */
    public final T convert(final S source, final ConversionContext ctx) {
        Validate.notNull(source);
        U intermediateValue = converterA.convert(source, ctx);
        T ret = null;
        if (propagateNull || intermediateValue != null) {
            ret = converterB.convert(intermediateValue, ctx);
        }
        return ret;
    }

}

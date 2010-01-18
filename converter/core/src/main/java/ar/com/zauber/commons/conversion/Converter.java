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
package ar.com.zauber.commons.conversion;

/**
 * Converts an object to another one of a different type. Since a
 * <code>Converter</code>  represents a particular transformation, there
 * could be more than one <code>Converter</code> for any pair of types.
 * 
 * @param <S>
 *            Source type of the conversion.
 * @param <T>
 *            Target type of the conversion.
 * 
 * @author Mariano Cortesi
 * @since Nov 5, 2009
 */
public interface Converter<S, T> {

    /**
     * 
     * Converts the source object to a new instance of the target type,
     * getting from a <code>ConversionContext</code> any extra information
     * it may need.
     * 
     * @param ctx
     *            context of the conversion.
     */
    T convert(S source, ConversionContext ctx);
}

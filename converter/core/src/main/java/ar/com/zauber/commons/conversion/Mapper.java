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
 * 
 * Objects implementing this interface are capable of mapping a source object
 * to a destiny object of different type, taking into account a
 * <code>ConversionContext</code>.
 * 
 * Unlike the conversion done by a <code>Converter</code>, this kind of mapping
 * requires an existing instance of the target type.
 * 
 * @param <S>   Source class.
 * @param <T>   Target class.
 * 
 * @author Juan Edi
 * @author Mariano A. Cortesi
 * @since Nov 4, 2009
 */
public interface Mapper<S, T> {

    /**
     * 
     * Populates the target object taking into account the
     * <code>ConversionContext</code>
     * 
     * @param ctx contexto de conversion
     */
    void map(S source, T target, ConversionContext ctx);
    
}

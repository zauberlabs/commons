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
package ar.com.zauber.commons.dao;

import java.util.function.Function;

/**
 * An object capable of transforming an input object into some output object.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Jun 19, 2009
 * @param <I> Input entity class
 * @param <O> Output entity class
 */
@FunctionalInterface
public interface Transformer<I, O>  extends Function<I, O> {

    /** Transforms the input object (leaving it unchanged) into some output object.
     * @return the transformation of the input object to the output object
     */
   O transform(I input);
   
   default O apply(I input) {
       return transform(input);
   }
}

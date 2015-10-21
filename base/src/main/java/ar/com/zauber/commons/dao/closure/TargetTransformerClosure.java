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
package ar.com.zauber.commons.dao.closure;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.commons.dao.Transformer;

/**
 * {@link Closure} that transform it's input from type I to type T using a
 * {@link Transformer}, and then delegate the new object to a target {@link Closure}.
 * 
 * @author Juan F. Codagnone
 * @since Jun 23, 2009
 * @param <I> Input Type 
 * @param <T> Output Type
 */
public class TargetTransformerClosure<I, T> implements Closure<I> {
    private final Transformer<I, T> transformer;
    private final Closure<T> target;
    
    /** 
     * @param transformer transformer used to transform the input of the closure
     */
    public TargetTransformerClosure(final Transformer<I, T> transformer,
            final Closure<T> target) {
        Validate.notNull(transformer);
        Validate.notNull(target);
        
        this.transformer = transformer;
        this.target = target;
    }
    /** @see Closure#execute(Object) */
    public final void execute(final I input) {
        target.execute(transformer.transform(input));
    }
}

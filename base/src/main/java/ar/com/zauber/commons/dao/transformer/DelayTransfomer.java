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
package ar.com.zauber.commons.dao.transformer;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.AsyncronymousTransformer;
import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.commons.dao.Transformer;

/**
 * {@link Transformer} asyncronico.
 * 
 * @author Juan F. Codagnone
 * @since May 21, 2010
 * @param <I> tipo del parametro del input
 * @param <O> tipo del parametro de salida
 */
public class DelayTransfomer<I, O> implements AsyncronymousTransformer<I, O> {
    private final Transformer<I, O> transformer;

    /** Creates the SimpleAsyncronymousTransfomer. */
    public DelayTransfomer(final Transformer<I, O> transformer) {
        Validate.notNull(transformer);
        
        this.transformer = transformer;
    }
    
    /** @see AsyncronymousTransformer#transform(Object, Closure) */
    public final void transform(final I input, final Closure<O> closure) {
        closure.execute(transformer.transform(input));
    }

}

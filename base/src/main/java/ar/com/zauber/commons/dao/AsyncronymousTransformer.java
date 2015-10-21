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

/**
 * Similar a {@link Transformer} con la salvedad que la salida puede ser procesada
 * en forma asincrónica por un {@link Closure}.
 * 
 * @author Juan F. Codagnone
 * @since May 21, 2010
 * @param <I> tipo de parámetro de entrada
 * @param <O> tipo de parámetro de salida
 */
public interface AsyncronymousTransformer<I, O> {

    /** Procesa un parametro, y deja un resultado en un closure */
    void transform(I input, Closure<O> closure);
}

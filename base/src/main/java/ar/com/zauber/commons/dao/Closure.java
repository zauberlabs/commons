/**
 * Copyright (c) 2005-2012 Zauber S.A. <http://www.zaubersoftware.com/>
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
 * Like the one in commons-collections but with generic support.
 * 
 * <p> Defines a functor interface implemented by classes that do something.
 * </p><p>
 * A Closure represents a block of code which is executed from inside some 
 * block, function or iteration. It operates an input object.
 * </p><p>
 * Es una excelente elección para los metodos que retornan datos.
 * En vez de retornar una coleccion, pasarle como parametro un closure. 
 * Da mayor flexibilidad (si se quiere evitar se puede evitar
 * el uso de memoria que acarrea una colección).
 * 
 * </p>
 * @author Juan F. Codagnone
 * @since Apr 2, 2006
 * @param <T> generic type
 */
public interface Closure<T> {
    
    /**  closure */
    void execute(T t);
}

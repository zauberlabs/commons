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
package ar.com.zauber.commons.dao.chainedstorage;

/**
 * Interfaz que representa un contenedor de cierto tipo de elementos que se 
 * guardan indizados por un elemento clave.
 * 
 * @param <K> el tipo de dato de los identificadores
 * @param <V> el tipo de dato de la información
 * 
 * @author Pablo Grigolatto, Marcelo Turrin
 * @since Jun 18, 2010
 */
public interface Storage<K, V> {
    
    /** Almacena el valor para la clave dada según corresponda al contenedor */
    void store(K key, V value);
    
}
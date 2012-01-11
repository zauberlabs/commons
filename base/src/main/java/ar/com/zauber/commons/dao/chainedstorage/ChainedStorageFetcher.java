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
 * Interfaz que permite encadenar {@link Storage} de manera que si un
 * {@link Storage} no tiene el dato dado por la clave, llama al m�todo
 * {@link #fetch(Object, Storage)} de un {@link ChainedStorageFetcher} pas�ndose
 * como par�metro a si mismo, para que cuando este lo encuentre, llame al
 * {@link #store(Object, Object)} del original.
 * 
 * @param <K> el tipo de dato de los identificadores
 * @param <V> el tipo de dato de la informaci�n
 * 
 * @author Pablo Grigolatto, Marcelo Turrin
 * @since Jun 18, 2010
 */
public interface ChainedStorageFetcher<K, V> extends Storage<K, V> {
    /**
     * M�todo de b�squeda que encadena (busco en el m�o y si encuentro llamo al
     * {@link #store(Object, Object)} del parentStorage.
     * 
     * @param parentStorage
     *            un {@link Storage} al cual usar como contenedor, 
     *            que se llamar� al encontrar el dato
     * @throws IllegalArgumentException
     *            si el par�metro parentStorage es null
     */
    void fetch(K key, Storage<K, V> parentStorage);
}
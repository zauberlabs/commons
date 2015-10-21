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
package ar.com.zauber.commons.dao.chainedstorage.impl;

import ar.com.zauber.commons.dao.chainedstorage.ChainedStorageFetcher;
import ar.com.zauber.commons.dao.chainedstorage.Storage;

/**
 * Clase utilitaria que implementa {@link ChainedStorageFetcher} que sirve para
 * colocar al final de una cadena cuando se supone que debería terminar antes.
 * Si se llega a una instancia de esta clase, esta tira
 * {@link UnsupportedOperationException} para avisar del fracaso.
 * 
 * @param <K> el tipo de dato de los identificadores
 * @param <V> el tipo de dato de la información
 * 
 * @author Pablo Grigolatto, Marcelo Turrin
 * @since Jun 18, 2010
 */
public class ExceptionChainedStorageFetcher<K, V> implements
        ChainedStorageFetcher<K, V> {

    /** @see Storage#store(Object, Object) */
    public final void store(final K key, final V value) {
        throw new UnsupportedOperationException("Should not reach this code");
    }

    /** @see ChainedStorageFetcher#fetch(Object, Storage) */
    public final void fetch(final K key, final Storage<K, V> parent) {
        throw new UnsupportedOperationException("Should not reach this code");
    }
}
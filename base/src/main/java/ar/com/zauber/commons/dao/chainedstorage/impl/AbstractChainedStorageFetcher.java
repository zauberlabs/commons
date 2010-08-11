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
package ar.com.zauber.commons.dao.chainedstorage.impl;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.chainedstorage.ChainedStorageFetcher;
import ar.com.zauber.commons.dao.chainedstorage.Storage;

/**
 * Clase abstracta que realiza la operación de encadenamiento de
 * {@link ChainedStorageFetcher}. Para eso lo que realiza es la pregunta
 * "Yo lo tengo". Si lo tiene llama al padre con el dato. Si no lo tiene se
 * guarda al padre y llama al hijo, "encadenándose" al pasarse como parámetro de
 * {@link #fetch(Object, Storage)}. Cuando el hijo le devuelve el control
 * llamando a {@link #store(Object, Object)}, este se guarda el dato y continua
 * la cadena llamado al {@link #store(Object, Object)} del padre. Para preguntar
 * "tengo?" y "guardar" delega en sus implementaciones los métodos abstractos.
 * 
 * @param <K> el tipo de dato de los identificadores
 * @param <V> el tipo de dato de la información
 * 
 * @author Pablo Grigolatto, Marcelo Turrin
 * @since Jun 18, 2010
 */
public abstract class AbstractChainedStorageFetcher<K, V> 
    implements ChainedStorageFetcher<K, V> {
    private final ChainedStorageFetcher<K, V> child;
    private Storage<K, V> parent;

    /** Creates the AbstractChainedStorageFetcher. */
    public AbstractChainedStorageFetcher(final ChainedStorageFetcher<K, V> child) {
        Validate.notNull(child);
        this.child = child;
    }

    /** @see ChainedStorageFetcher#fetch(Object, Storage) */
    public final void fetch(final K key, final Storage<K, V> parentStorage) {
        Validate.notNull(parentStorage);
        this.parent = parentStorage;

        V value = this.get(key);
        if (value != null) {
            this.parent.store(key, value);
        } else {
            child.fetch(key, this);
        }
    }

    /** @see Storage#publish(Object) */
    public final void store(final K key, final V value) {
        this.put(key, value);
        this.parent.store(key, value);
    }

    /** Busca el valor correspondiente a la clave dada */
    protected abstract V get(K key);

    /** Guarda el par clave valor */
    protected abstract void put(K key, V value);
}
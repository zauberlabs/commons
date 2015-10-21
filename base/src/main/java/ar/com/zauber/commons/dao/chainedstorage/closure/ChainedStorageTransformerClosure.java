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
package ar.com.zauber.commons.dao.chainedstorage.closure;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.commons.dao.chainedstorage.ChainedStorageFetcher;
import ar.com.zauber.commons.dao.chainedstorage.Storage;

/**
 * {@link Closure} que se encarga de convertir un dato en otro buscando,
 * delegando la búsqueda en una serie de {@link ChainedStorageFetcher}. 
 * Cuando la búsqueda está terminada y la cadena devuelve el dato 
 * (mediante el llamado a {@link #store(K, V)} de esta instancia) 
 * se continua la cadena de {@link Closure} 
 * llamando a aquel que se pasó por constructor
 * 
 * @param <K> el tipo de dato de los identificadores
 * @param <V> el tipo de dato de la información
 *  
 * @author Pablo Grigolatto, Marcelo Turrin
 * @since Jun 18, 2010
 */
public class ChainedStorageTransformerClosure<K, V> implements Storage<K, V>,
        Closure<K> {

    private final ChainedStorageFetcher<K, V> child;
    private final Closure<V> target;

    /** Creates the ChainedStorageTransformerClosure. */
    public ChainedStorageTransformerClosure(
            final ChainedStorageFetcher<K, V> child, final Closure<V> target) {
        super();
        Validate.notNull(target);
        Validate.notNull(child);
        this.child = child;
        this.target = target;
    }

    /** @see Closure#execute(Object) */
    public final void execute(final K key) {
        child.fetch(key, this);
    }

    /** @see Storage#store(Object, Object) */
    public final void store(final K key, final V value) {
        target.execute(value);
    }
}
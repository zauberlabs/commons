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
package ar.com.zauber.commons.dao.chainedstorage.impl;

import java.util.Map;

import ar.com.zauber.commons.dao.chainedstorage.ChainedStorageFetcher;
import ar.com.zauber.commons.dao.chainedstorage.Storage;

/**
 * Clase que implementa {@link AbstractChainedStorageFetcher} 
 * para un {@link Storage} sobre {@link Map}.
 * 
 * @param <K> la key para almacenar
 * @param <V> el value 
 * @author Pablo Grigolatto, Marcelo Turrin
 * @since Jun 18, 2010
 */
public class MapBasedChainedStorageFetcher<K, V> extends
        AbstractChainedStorageFetcher<K, V> {

    private final Map<K, V> repository;

    /** Creates the MapBasedChainedStorageFetcher. */
    public MapBasedChainedStorageFetcher(
            final ChainedStorageFetcher<K, V> child, final Map<K, V> initialMap) {
        super(child);
        repository = initialMap;
    }

    /** @see AbstractChainedStorageFetcher#get(Object) */
    @Override
    protected final V get(final K input) {
        return repository.get(input);
    }

    /** @see AbstractChainedStorageFetcher#put(Object, Object) */
    @Override
    protected final void put(final K input, final V output) {
        this.repository.put(input, output);
    }
}

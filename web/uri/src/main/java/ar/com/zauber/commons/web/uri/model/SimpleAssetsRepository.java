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
package ar.com.zauber.commons.web.uri.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * {@link AssetRepository}'s implementation that stores each added asset
 * for each set, and then returns them when asked.
 * 
 * @author Mariano Cortesi
 * @since Apr 23, 2010
 */
public class SimpleAssetsRepository implements AssetRepository {

    private final Map<String, List<AssetModel>> assets = 
        new HashMap<String, List<AssetModel>>();
    private final Logger logger = 
        LoggerFactory.getLogger(SimpleAssetsRepository.class); 

    /** @see AssetRepository#addAsset(String, AssetModel) */
    public final void addAsset(final String set, final AssetModel asset) {
        if (!this.assets.containsKey(set)) {
            this.assets.put(set, new LinkedList<AssetModel>());
        }
        this.assets.get(set).add(asset);
    }

    /** @see AssetRepository#getSet(String) */
    public final List<AssetModel> getSet(final String set) {
        List<AssetModel> result = this.assets.get(set);
        if(result == null) {
            result = Collections.emptyList();
            this.logger.warn("Se pidió un Set de assets vacio (probablemente se"
                    + " llamó a printTag sin haber agregado un asset)");
        }
        return Collections.unmodifiableList(result);
    }
}

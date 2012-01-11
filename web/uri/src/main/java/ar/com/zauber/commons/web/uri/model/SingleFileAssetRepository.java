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
package ar.com.zauber.commons.web.uri.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.Validate;

/**
 * {@link AssetRepository}'s implementation that uses a single file per
 * AssetModel type for each assets set. It doesn't matter how many files were
 * added.
 * 
 * @author Mariano Cortesi
 * @since May 7, 2010
 */
public class SingleFileAssetRepository implements AssetRepository {

    private final Map<String, List<AssetModel>> predefinedAssets;
    private final Map<Class<? extends AssetModel>, Set<String>> typesIncluded;

    /**
     * Creates the SingleFileAssetRepository, setting the predefined assets
     * for each set.
     * 
     * @param predefinedAssets {@link Map} of predefined assets.
     */
    public SingleFileAssetRepository(
            final Map<String, List<AssetModel>> predefinedAssets) {
        Validate.notNull(predefinedAssets);
        this.typesIncluded = new HashMap<Class<? extends AssetModel>, Set<String>>();
        this.predefinedAssets = predefinedAssets;
    }

    /** @see AssetRepository#addAsset(java.lang.String, AssetModel) */
    public final void addAsset(final String set, final AssetModel asset) {
        if (!typesIncluded.containsKey(asset.getClass())) {
            typesIncluded.put(asset.getClass(), new HashSet<String>());
        }
        final Set<String> type = typesIncluded.get(asset);
        if(type == null) {
            throw new IllegalStateException("No configuration for " + asset);
        }
        type.add(set);
    }

    /** @see AssetRepository#getSet(String) */
    public final List<AssetModel> getSet(final String set) {
        final List<AssetModel> predefined = predefinedAssets.get(set);
        Validate.notNull(predefined, "no predefined assets for set: " + set);

        final List<AssetModel> assets = new ArrayList<AssetModel>(predefined.size());
        for(final AssetModel assetModel : predefined) {
            if(typesIncluded.containsKey(assetModel.getClass()) 
                    && typesIncluded.get(assetModel.getClass()).contains(set)) {
                assets.add(assetModel);
            }
        }
        
        return assets;
    }
}

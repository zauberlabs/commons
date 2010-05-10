/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
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
        if (!this.typesIncluded.containsKey(asset.getClass())) {
            this.typesIncluded.put(asset.getClass(), new HashSet<String>());
        }
        this.typesIncluded.get(asset).add(set);
    }

    /** @see AssetRepository#getSet(String) */
    public final List<AssetModel> getSet(final String set) {
        List<AssetModel> predefined = this.predefinedAssets.get(set);
        Validate.notNull(predefined, "no predefined assets for set: " + set);

        List<AssetModel> assets = new ArrayList<AssetModel>(predefined.size());
        for (AssetModel assetModel : predefined) {
            if (this.typesIncluded.containsKey(assetModel.getClass()) 
                    && this.typesIncluded.get(assetModel.getClass()).contains(set)) {
                assets.add(assetModel);
            }
        }
        
        return assets;
    }

}

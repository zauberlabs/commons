/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


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

    /** @see AssetRepository#addAsset(String, AssetModel) */
    public final void addAsset(final String set, final AssetModel asset) {
        if (!assets.containsKey(set)) {
            assets.put(set, new LinkedList<AssetModel>());
        }
        assets.get(set).add(asset);
    }

    /** @see AssetRepository#getSet(String) */
    public final List<AssetModel> getSet(final String set) {
        return Collections.unmodifiableList(this.assets.get(set));
    }
}

/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.assets;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import ar.com.zauber.commons.web.uri.assets.model.AssetModel;

/**
 * Repository of Asset's definitions
 * 
 * @author Mariano Cortesi
 * @since Apr 23, 2010
 */
public class AssetsRepository {

    private final Map<String, List<AssetModel>> assets = 
        new HashMap<String, List<AssetModel>>();

    /**
     * Adds an asset to the AssetRepository
     */
    public final void addAsset(final String set, final AssetModel asset) {
        if (!assets.containsKey(set)) {
            assets.put(set, new LinkedList<AssetModel>());
        }
        assets.get(set).add(asset);
    }

    /** @return A whole set of assets */
    public final List<AssetModel> getSet(final String set) {
        return Collections.unmodifiableList(this.assets.get(set));
    }
    
    /**
     * <p>
     * Repository Locator in the {@link PageContext}.
     * 
     * <p>
     * If no {@link AssetsRepository} is found, a new one is created and stored.
     */
    public static final AssetsRepository getOrCreate(
            final PageContext pageContext, final String key) {
        Object value = pageContext.findAttribute(key);

        AssetsRepository repository = null;
        if (value == null) {
            repository = new AssetsRepository();
            pageContext.getRequest().setAttribute(key, repository);
        } else if (!AssetsRepository.class.isAssignableFrom(value.getClass())) {
            throw new IllegalStateException(
                    "Invalid repository stored in pageContext key " + key);
        } else {
            repository = (AssetsRepository) value;
        }
        return repository;
    }
}

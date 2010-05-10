/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.model;

import java.util.List;

/**
 * <p>Repository of Asset's definitions. 
 * 
 * <p>A repository accumulates assets inclusions for different "sets" of 
 * assets. Afterwards, is able to return the list of assets for a certain set.
 * 
 * @author Mariano Cortesi
 * @since May 7, 2010
 */
public interface AssetRepository {

    /** Adds an asset to a set */
    void addAsset(final String set, final AssetModel asset);

    /** @return A whole set of assets */
    List<AssetModel> getSet(final String set);

}
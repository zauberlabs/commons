/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.model;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.uri.factory.UriFactory;

/**
 * Models an Asset
 * 
 * @author Mariano Cortesi
 * @since Apr 23, 2010
 */
public abstract class AssetModel {

    private final String key;
    
    /** Creates the AssetModel. */
    public AssetModel(final String key) {
        Validate.notNull(key);
        this.key = key;
    }

    public final String getKey() {
        return this.key;
    }

    /** Transforms the {@link AssetModel} into the proper HTML include tag */
    public abstract String toHtml(UriFactory uriFactory);
}

/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.assets;

import ar.com.zauber.commons.web.uri.assets.model.AssetModel;
import ar.com.zauber.commons.web.uri.assets.model.JavascriptAsset;

/**
 * Marks a Javascript asset to be included within the jsp page.
 * 
 * @author Mariano Cortesi
 * @since Dec 14, 2009
 */
public class JavascriptTag extends AssetIncludeTag {

    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = 7382501689538531396L;

    /** @see AssetIncludeTag#getAsset() */
    @Override
    protected final AssetModel getAsset() {
        return new JavascriptAsset(getKey());
    }

}

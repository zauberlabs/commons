/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.assets;

import ar.com.zauber.commons.web.uri.model.AssetModel;
import ar.com.zauber.commons.web.uri.model.CssAsset;

/**
 * Marks a css asset to be included within the jsp page.
 * 
 * @author Mariano Cortesi
 * @since Dec 14, 2009
 */
public class CssTag extends AssetIncludeTag {

    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = -706464019083783327L;
    
    private String media = "all";

    public final void setMedia(final String media) {
        this.media = media;
    }

    /** @see AssetIncludeTag#getAsset() */
    @Override
    protected final AssetModel getAsset() {
        return new CssAsset(getKey(), this.media);
    }

}

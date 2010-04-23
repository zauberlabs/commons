/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.assets.model;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.uri.WebContext;

/**
 * Models a Css Asset
 * 
 * @author Mariano Cortesi
 * @since Apr 23, 2010
 */
public class CssAsset extends AssetModel {

    private String media;

    /** Creates the CssAsset. */
    public CssAsset(final String key, final String media) {
        super(key);
        Validate.notNull(media);
        this.media = media;
    }

    /** @see AssetModel#toHtml(WebContext) */
    @Override
    public final String toHtml(final WebContext webContext) {
        StringBuilder str = new StringBuilder();
        str.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
        str.append(webContext.getContext());
        str.append(this.getKey());
        str.append("?v=");
        str.append(webContext.getVersion());
        str.append("\" media=\"")
           .append(this.media)
           .append("\" />");
        return str.toString();
    }

}

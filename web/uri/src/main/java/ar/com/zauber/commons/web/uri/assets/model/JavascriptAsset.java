/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.assets.model;

import ar.com.zauber.commons.web.uri.WebContext;

/**
 * Models a Javascript Asset
 * 
 * @author Mariano Cortesi
 * @since Apr 23, 2010
 */
public class JavascriptAsset extends AssetModel {

    /** Creates the JavascriptAsset. */
    public JavascriptAsset(final String key) {
        super(key);
    }

    /** @see AssetModel#toHtml(WebContext) */
    @Override
    public final String toHtml(final WebContext webContext) {
        StringBuilder str = new StringBuilder();
        str.append("<script type=\"text/javascript\" src=\"");
        str.append(webContext.getContext());
        str.append(this.getKey());
        str.append("?v=");
        str.append(webContext.getVersion());
        str.append("\"></script>");
        return str.toString();
    }

}

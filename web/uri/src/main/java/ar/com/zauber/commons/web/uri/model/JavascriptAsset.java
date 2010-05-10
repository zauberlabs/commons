/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.model;

import ar.com.zauber.commons.web.uri.factory.UriFactory;

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
    public final String toHtml(final UriFactory uriFactory) {
        StringBuilder str = new StringBuilder();
        str.append("<script type=\"text/javascript\" src=\"");
        str.append(uriFactory.buildUri(getKey()));
        str.append("\"></script>");
        return str.toString();
    }

}

/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.model;


/**
 * Models a header asset.
 * 
 * @author Mariano Semelman
 * @since Jun 17, 2010
 */
public abstract class HeaderAsset extends AssetModel {

    private String charset;

    /**
     * Creates the HeaderAsset.
     * @param key de este asset
     * @param charset de este asset
     */
    public HeaderAsset(final String key, final String charset) {
        super(key);
        this.charset = charset;
    }

    public String getCharset() {
        return charset;
    }

}

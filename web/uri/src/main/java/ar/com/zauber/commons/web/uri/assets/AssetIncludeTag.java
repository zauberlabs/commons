/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.assets;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import ar.com.zauber.commons.web.uri.assets.model.AssetModel;

/**
 * Abstract tag to make {@link AssetModel} inclusions.
 * 
 * @author Mariano Cortesi
 * @since Apr 23, 2010
 */
public abstract class AssetIncludeTag extends TagSupport {

    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1273787500463849908L;
    
    private String set = Assets.DEFAULT_SET;
    private String repository = Assets.REPOSITORY_KEY;
    private String key;

    /** @see javax.servlet.jsp.tagext.TagSupport#doStartTag() */
    public final int doStartTag() throws JspException {
        AssetsRepository assetsRepository = 
            AssetsRepository.getOrCreate(this.pageContext, repository);
        
        assetsRepository.addAsset(this.set, getAsset());
        return Tag.SKIP_BODY;
    }    
    
    /** @return {@link AssetModel} to include */
    protected abstract AssetModel getAsset();

    public final void setRepository(final String repository) {
        this.repository = repository;
    }

    public final void setKey(final String key) {
        this.key = key;
    }

    public final void setSet(final String set) {
        this.set = set;
    }

    public final String getKey() {
        return this.key;
    }

}

/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.assets;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import org.springframework.web.context.WebApplicationContext;

import ar.com.zauber.commons.web.uri.SpringBeans;
import ar.com.zauber.commons.web.uri.model.AssetModel;
import ar.com.zauber.commons.web.uri.model.AssetRepository;

/**
 * <p>Abstract tag to make {@link AssetModel} inclusions.
 * 
 * <p>To register the included assets, the tags depends on a 
 * {@link AssetRepository} that should be defined as a Spring Bean
 * with the key defined in {@link SpringBeans}. This bean, must be
 * defined with <b>request</b> scope.
 * 
 * @author Mariano Cortesi
 * @since Apr 23, 2010
 */
public abstract class AssetIncludeTag extends AbstractSpringTag {

    /** Name of the default set */
    public static final String DEFAULT_SET = "_default_";
    
    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1273787500463849908L;
    
    private String set = DEFAULT_SET;
    private String key;

    /** @see javax.servlet.jsp.tagext.TagSupport#doStartTag() */
    public final int doStartTag() throws JspException {
        WebApplicationContext appCtx = getApplicationContext();
        AssetRepository repository = appCtx.getBean(SpringBeans.REPOSITORY_KEY,
                AssetRepository.class); 
        
        repository.addAsset(this.set, getAsset());
        return Tag.SKIP_BODY;
    }    
    
    /** @return {@link AssetModel} to include */
    protected abstract AssetModel getAsset();

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

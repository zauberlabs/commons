/*
 * Copyright (c) 2009 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.assets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import ar.com.zauber.commons.web.uri.WebContext;
import ar.com.zauber.commons.web.uri.assets.model.AssetModel;

/**
 * Prints all included javascript and CSS assests into the HTML.
 * 
 * 
 * @author Mariano Cortesi
 * @since Dec 14, 2009
 */
public class PrintTag extends TagSupport {

    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = -3624874122306975946L;

    private String set = Assets.DEFAULT_SET;
    private String repository = Assets.REPOSITORY_KEY;
    private String version = Assets.APPVERSION_KEY;
    private String context = Assets.WEBCONTEXT_KEY;

    /** @see javax.servlet.jsp.tagext.TagSupport#doStartTag() */
    @Override
    public final int doStartTag() throws JspException {
        AssetsRepository assetsRepository = 
            AssetsRepository.getOrCreate(this.pageContext, repository);
        
        WebContext webContext = (WebContext) 
            this.pageContext.findAttribute(this.context);
        
        if (webContext == null) {
            String contextPath = 
                ((HttpServletRequest) this.pageContext.getRequest())
                .getContextPath();
            String appVersion = (String) this.pageContext.findAttribute(version);
            webContext = new WebContext(contextPath, appVersion);
        }
        
        JspWriter out = this.pageContext.getOut();
        try {
            for (AssetModel asset : assetsRepository.getSet(this.set)) {
                out.write(asset.toHtml(webContext));
            }
        } catch (IOException e) {
            throw new JspException(e);
        }
        return Tag.SKIP_BODY;
    }

    public final void setRepository(final String repository) {
        this.repository = repository;
    }

    public final void setContext(final String context) {
        this.context = context;
    }

    public final void setSet(final String set) {
        this.set = set;
    }
}

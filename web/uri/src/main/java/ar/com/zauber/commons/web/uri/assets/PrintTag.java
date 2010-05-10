/*
 * Copyright (c) 2009 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.assets;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;

import org.springframework.web.context.WebApplicationContext;

import ar.com.zauber.commons.web.uri.SpringBeans;
import ar.com.zauber.commons.web.uri.factory.UriFactory;
import ar.com.zauber.commons.web.uri.model.AssetModel;
import ar.com.zauber.commons.web.uri.model.AssetRepository;

/**
 * Prints all included javascript and CSS assests into the HTML.
 * 
 * @author Mariano Cortesi
 * @since Dec 14, 2009
 */
public class PrintTag extends AbstractSpringTag {

    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = -3624874122306975946L;

    private String set = Assets.DEFAULT_SET;

    /** @see javax.servlet.jsp.tagext.TagSupport#doStartTag() */
    @Override
    public final int doStartTag() throws JspException {
        WebApplicationContext appCtx = getApplicationContext();
        AssetRepository repository = appCtx.getBean(SpringBeans.REPOSITORY_KEY,
                AssetRepository.class); 
        UriFactory uriFactory = appCtx.getBean(SpringBeans.ASSET_URIFACTORY_KEY,
                UriFactory.class); 
        
        JspWriter out = this.pageContext.getOut();
        try {
            for (AssetModel asset : repository.getSet(this.set)) {
                out.write(asset.toHtml(uriFactory));
            }
        } catch (IOException e) {
            throw new JspException(e);
        }
        return Tag.SKIP_BODY;
    }

    public final void setSet(final String set) {
        this.set = set;
    }
}

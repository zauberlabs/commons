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

/**
 * Prints an image url using an image key.
 * 
 * 
 * @author Mariano Cortesi
 * @since Dec 14, 2009
 */
public class ImageTag extends AbstractSpringTag {

    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = -3624874122306975946L;

    private String key;

    /** @see javax.servlet.jsp.tagext.TagSupport#doStartTag() */
    @Override
    public final int doStartTag() throws JspException {
        WebApplicationContext appCtx = getApplicationContext();
        UriFactory uriFactory = appCtx.getBean(SpringBeans.ASSET_URIFACTORY_KEY,
                UriFactory.class); 
        
        JspWriter out = this.pageContext.getOut();
        try {
            out.write(uriFactory.buildUri(this.key));
        } catch (IOException e) {
            throw new JspException(e);
        }

        return Tag.SKIP_BODY;
    }

    public final void setKey(final String key) {
        this.key = key;
    }

}

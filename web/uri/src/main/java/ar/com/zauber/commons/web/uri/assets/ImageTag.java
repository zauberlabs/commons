/*
 * Copyright (c) 2009 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.assets;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import ar.com.zauber.commons.web.uri.WebContext;

/**
 * Prints an image url using an image key.
 * 
 * 
 * @author Mariano Cortesi
 * @since Dec 14, 2009
 */
public class ImageTag extends TagSupport {

    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = -3624874122306975946L;

    private String key;
    private String context = Assets.WEBCONTEXT_KEY;

    /** @see javax.servlet.jsp.tagext.TagSupport#doStartTag() */
    @Override
    public final int doStartTag() throws JspException {
        WebContext ctx = (WebContext) this.pageContext.findAttribute(context);

        StringBuilder str = new StringBuilder();
        str.append(ctx.getContext());
        str.append(this.key);
        str.append("?v=");
        str.append(ctx.getVersion());

        JspWriter out = this.pageContext.getOut();
        try {
            out.write(str.toString());
        } catch (IOException e) {
            throw new JspException(e);
        }

        return Tag.SKIP_BODY;
    }

    public final void setContext(final String context) {
        this.context = context;
    }

    public final void setKey(final String key) {
        this.key = key;
    }

}

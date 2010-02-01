/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.assets;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Marks a Javascript asset to be included within the jsp page.
 * 
 * @author Mariano Cortesi
 * @since Dec 14, 2009
 */
public class JavascriptTag extends TagSupport {

    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1713257254742184480L;
    
    private String property = Assets.JS_KEY;
    private String key;
    
    /** @see javax.servlet.jsp.tagext.TagSupport#doStartTag() */
    @SuppressWarnings("unchecked")
    @Override
    public final int doStartTag() throws JspException {
        List<String> javascripts = 
            (List<String>) this.pageContext.findAttribute(property);
        if (javascripts == null) {
            javascripts = new LinkedList<String>();
            this.pageContext.getRequest().setAttribute(property, javascripts);
        }
        javascripts.add(key);
        return Tag.SKIP_BODY;
    }

    public final void setProperty(final String property) {
        this.property = property;
    }

    public final void setKey(final String key) {
        this.key = key;
    }
}

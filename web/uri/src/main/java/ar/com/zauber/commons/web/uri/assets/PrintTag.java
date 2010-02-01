/*
 * Copyright (c) 2009 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.assets;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import ar.com.zauber.commons.web.uri.WebContext;

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
    
    private String jsProperty = Assets.JS_KEY;
    private String cssProperty = Assets.CSS_KEY;
    private String context = Assets.WEBCONTEXT_KEY;

    /** @see javax.servlet.jsp.tagext.TagSupport#doStartTag() */
    @SuppressWarnings("unchecked")
    @Override
    public final int doStartTag() throws JspException {
        List<String> javascripts = (List<String>) this.pageContext
                .findAttribute(jsProperty);
        List<String> csss = (List<String>) this.pageContext
                .findAttribute(cssProperty);
        WebContext ctx = (WebContext) this.pageContext.findAttribute(context);

        if (javascripts != null) {
            JspWriter out = this.pageContext.getOut();
            for (String jsKey : javascripts) {
                StringBuilder str = new StringBuilder();
                str.append("<script type=\"text/javascript\" src=\"");
                str.append(ctx.getContext());
                str.append(jsKey);
                str.append("?v=");
                str.append(ctx.getVersion());
                str.append("\"></script>");
                try {
                    out.write(str.toString());
                } catch (IOException e) {
                    throw new JspException(e);
                }
            }
        }

        if (csss != null) {
            JspWriter out = this.pageContext.getOut();
            for (String cssKey : csss) {
                StringBuilder str = new StringBuilder();
                str.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
                str.append(ctx.getContext());
                str.append(cssKey);
                str.append("?v=");
                str.append(ctx.getVersion());
                str.append("\" media=\"all\" />");
                try {
                    out.write(str.toString());
                } catch (IOException e) {
                    throw new JspException(e);
                }
            }
        }

        return Tag.SKIP_BODY;
    }

    public final void setJsProperty(final String jsProperty) {
        this.jsProperty = jsProperty;
    }

    public final void setContext(final String context) {
        this.context = context;
    }

    public final void setCssProperty(final String cssProperty) {
        this.cssProperty = cssProperty;
    }
}

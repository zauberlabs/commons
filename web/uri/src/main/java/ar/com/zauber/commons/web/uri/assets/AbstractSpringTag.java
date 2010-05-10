/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.assets;

import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * Abstract Tag with Spring Support
 * 
 * @author Mariano Cortesi
 * @since May 7, 2010
 */
public abstract class AbstractSpringTag extends TagSupport {

    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1526552888077046751L;

    /** @return Request {@link ApplicationContext} */
    protected final WebApplicationContext getApplicationContext() {
        return RequestContextUtils.getWebApplicationContext(
                this.pageContext.getRequest());
    }

}
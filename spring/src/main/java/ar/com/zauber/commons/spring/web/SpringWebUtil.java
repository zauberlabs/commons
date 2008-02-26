/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.spring.web;

import org.springframework.web.servlet.view.RedirectView;

import ar.com.zauber.commons.spring.servlet.view.PermanentlyRedirectView;

/**
 * Spring MVC utilities.
 * 
 * @author Andrés Moratti
 * @since Nov 8, 2005
 */
public  class SpringWebUtil {
    /** redirections are context relative? */
    private boolean contextRelative;

    /** Creates the SpringWebUtil. */
    public SpringWebUtil() {
        this(true);
    }
    
    /** 
     * Creates the SpringWebUtil. 
     * 
     * @param contextRelative redirects are contextRelative?
     */
    public SpringWebUtil(final boolean contextRelative) {
        this.contextRelative = contextRelative;
    }
    
    /**
     * @param url url to redirect to
     * @return a <code>RedirectView</code> object for url 
     */
    public final RedirectView createRedirect(final String url) {
        final RedirectView rv = new RedirectView(url);
        rv.setContextRelative(contextRelative);
        rv.setHttp10Compatible(false);
        
        return rv;
    }
    
    /**
     * @param url url to redirect to
     * @return a <code>RedirectView</code> object for url 
     */
    public final PermanentlyRedirectView createPermanentlyRedirect(final String url) {
        final PermanentlyRedirectView rv = new PermanentlyRedirectView(url);
        rv.setContextRelative(contextRelative);
        rv.setHttp10Compatible(false);
        
        return rv;
    }
}

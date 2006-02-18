/*
 * Copyright (c) 2005 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.spring.web;

import org.springframework.web.servlet.view.RedirectView;


/**
 * Spring MVC utilities.
 * 
 * @author Andrés Moratti
 * @since Nov 8, 2005
 */
public final class SpringWebUtil {

    /** Creates the SpringWebUtil. */
    private SpringWebUtil() {
        // utility class
    }
    
    /**
     * @param url url to redirect to
     * @return a <code>RedirectView</code> object for url 
     */
    public static RedirectView createRedirect(final String url) {
        final RedirectView rv = new RedirectView(url);
        rv.setContextRelative(true);
        rv.setHttp10Compatible(false);
        
        return rv;
    }
}

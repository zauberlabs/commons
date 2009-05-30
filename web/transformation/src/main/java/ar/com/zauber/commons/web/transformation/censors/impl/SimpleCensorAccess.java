/**
 *  Copyright (c) 2008-2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.transformation.censors.impl;

import java.util.List;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.transformation.censors.CensorAccess;

/**
 * Implementación tonta de {@link CensorAccess} que recibe un listado
 * fijo de uris para censurar.
 * 
 * @author Matías Tito
 * @since Oct 22, 2008
 */
public class SimpleCensorAccess extends AbstractCensorAccess  {
    private final List<String> forbidenURIs;

    /** Creates the SimpleCensorAccess. */
    public SimpleCensorAccess(final List<String> forbidenURIs) {
        Validate.noNullElements(forbidenURIs);
        
        this.forbidenURIs = forbidenURIs;
    }
    
    /** @see CensorAccess#canAccess(java.lang.String) */
    public final boolean canAccess(final String uri) {
        validate(uri);
        boolean ret = true;
        
        for(final String forbidenURI : forbidenURIs) {            
            if (forbidenURI.equals(uri)) {
                ret = false;
            }
        }
        
        return ret;
    }
}

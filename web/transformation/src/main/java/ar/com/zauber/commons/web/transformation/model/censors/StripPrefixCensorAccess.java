/**
 *  Copyright (c) 2008-2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.transformation.model.censors;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.transformation.model.CensorAccess;
import ar.com.zauber.commons.web.transformation.utils.WebValidate;

/**
 * Quita de las URIs que llegan un prefijo.
 * 
 * @author Alejandro Souto
 * @since 11/11/2008
 */
public class StripPrefixCensorAccess extends AbstractCensorAccess {
    private final String prefixToStrip;
    private final CensorAccess target;

    /** 
     * Creates the StripPrefixCensorAccess.
     *
     * @param prefixToStrip prefijo a quitar de todas las uris que llegan al censor
     * @param target {@link CensorAccess} posta posta
     */
    public StripPrefixCensorAccess(final String prefixToStrip, 
            final CensorAccess target) {
        WebValidate.uriNotBlank(prefixToStrip);
        Validate.notNull(target);
        
        this.prefixToStrip = prefixToStrip;
        this.target = target;
    }
    
    /** @see CensorAccess#canAccess(String) */
    public final boolean canAccess(final String uri) {
        String s;
        if(uri.startsWith(prefixToStrip)) {
            s = uri.substring(prefixToStrip.length());
        } else {
            s = uri;
        }
        return target.canAccess(s);
    }

}

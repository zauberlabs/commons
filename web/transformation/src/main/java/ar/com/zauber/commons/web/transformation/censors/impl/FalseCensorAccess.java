/**
 *  Copyright (c) 2008-2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.transformation.censors.impl;


/**
 * {@link CensorAccess} que siempre retorna <code>false</code>.
 * 
 * @author Matías Tito
 * @since Oct 31, 2008
 */
public class FalseCensorAccess extends AbstractCensorAccess {

    /** @see CensorAccess#canAccess(String) */
    public final boolean canAccess(final String uri) {
        validate(uri);
        
        return false;
    }

}

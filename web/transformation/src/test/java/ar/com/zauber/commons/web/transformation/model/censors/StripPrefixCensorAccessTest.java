/**
 *  Copyright (c) 2008-2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.transformation.model.censors;

import static org.junit.Assert.*;

import org.junit.Test;

import ar.com.zauber.commons.web.transformation.model.CensorAccess;
import ar.com.zauber.commons.web.transformation.model.censors.StripPrefixCensorAccess;
import junit.framework.TestCase;

/**
 * Tests {@link StripPrefixCensorAccess}.
 * 
 * @author Alejandro Souto
 * @since 11/11/2008
 */
public class StripPrefixCensorAccessTest {

    /** test */
    @Test
    public final void testSaca() {
        final CensorAccess censorAccess = new StripPrefixCensorAccess("/aaa", 
                new CensorAccess() {
            public boolean canAccess(final String uri) {
                assertEquals("/remote/hudson/pepe", uri);
                return false;
            }
        });
        
        censorAccess.canAccess("/aaa/remote/hudson/pepe");
    }
    
    /** test */
    @Test
    public final void testNoSaca() {
        final CensorAccess censorAccess = new StripPrefixCensorAccess("/aaa", 
                new CensorAccess() {
            public boolean canAccess(final String uri) {
                assertEquals("/bbbb/remote/hudson/pepe", uri);
                return false;
            }
        });
        
        censorAccess.canAccess("/bbbb/remote/hudson/pepe");
    }
}

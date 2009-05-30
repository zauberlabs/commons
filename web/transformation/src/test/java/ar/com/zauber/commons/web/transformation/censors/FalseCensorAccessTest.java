/**
 *  Copyright (c) 2008-2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.transformation.censors;

import org.junit.Test;

import ar.com.zauber.commons.web.transformation.censors.CensorAccess;
import ar.com.zauber.commons.web.transformation.censors.impl.FalseCensorAccess;

import static org.junit.Assert.*;

/**
 * Tests for {@link CensorAccess}.
 * 
 * 
 * @author Matías Tito
 * @since Nov 11, 2008
 */
public class FalseCensorAccessTest {

    /** un censor access no puede recibir uris relativas */
    @Test(expected = IllegalArgumentException.class)
    public final void testRelativeUri() {
        new FalseCensorAccess().canAccess("hola");
    }
    
    /** un censor access no puede recibir uris relativas */
    @Test
    public final void testURLHttp() {
        assertFalse(new FalseCensorAccess().canAccess("http://flof.com.ar/"));
    }
    
    /** un censor access no puede recibir uris relativas */
    @Test
    public final void testURLHttps() {
        assertFalse(new FalseCensorAccess().canAccess("https://mail.google.com/"));
    }
    
    /** un censor access no puede recibir uris relativas */
    @Test
    public final void testURLftp() {
        assertFalse(new FalseCensorAccess().canAccess("ftp://mail.google.com/"));
    }
}

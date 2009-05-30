/**
 *  Copyright (c) 2008-2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.transformation.utils;

import org.junit.Test;

import ar.com.zauber.commons.web.transformation.utils.WebValidate;

/**
 * TODO Descripcion de la clase. Los comenterios van en castellano.
 * 
 * 
 * @author Alejandro Souto
 * @since 12/11/2008
 */
public class WebValidateTest {

    /** test */
    @Test(expected = IllegalArgumentException.class)
    public final void testUriNotBlank() {
        WebValidate.uriNotBlank("");
    }

    /** test */
    @Test(expected = IllegalArgumentException.class)
    public final void testUriNotNull() {
        WebValidate.uriNotNull(null);
    }

    /** test */
    @Test(expected = IllegalArgumentException.class)
    public final void testUriWithLastSlash() {
        WebValidate.uriWellFormed("   /no/deberia/validar/   ");
    }
}

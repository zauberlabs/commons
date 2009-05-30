/**
 *  Copyright (c) 2008-2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.transformation.censors.impl;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.transformation.censors.CensorAccess;

/**
 * Clase base para implementaciones de {@link CensorAccess}.
 * 
 * @author Matías Tito
 * @since Nov 11, 2008
 */
public abstract class AbstractCensorAccess implements CensorAccess {

    /** uri */
    public final void validate(final String uri) {
        try {
            new URL(uri);
        } catch (final MalformedURLException e) {
            Validate.isTrue(uri.startsWith("/"),
                    "URI `" + uri + "' is not absolute");
        }
    }
}

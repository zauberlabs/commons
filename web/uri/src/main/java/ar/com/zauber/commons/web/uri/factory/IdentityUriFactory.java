/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.factory;

/**
 * {@link UriFactory} implementation that returns the key as the uri.
 * 
 * @author Mariano Cortesi
 * @since May 7, 2010
 */
public class IdentityUriFactory implements UriFactory {

    /** @see UriFactory#buildUri(String, Object[]) */
    public final String buildUri(final String uriKey, final Object... expArgs) {
        return uriKey;
    }

}

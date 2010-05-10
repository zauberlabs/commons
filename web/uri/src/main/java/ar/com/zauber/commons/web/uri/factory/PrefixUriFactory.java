/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.factory;

/**
 * {@link UriFactory} implementation that adds a prefix to the uri generated
 * by another {@link UriFactory}.
 * 
 * @author Mariano Cortesi
 * @since May 7, 2010
 */
public class PrefixUriFactory implements UriFactory {

    private final String prefix;
    private final UriFactory uriFactory;
    
    
    /**
     * Creates the PrefixUriFactory.
     *
     * @param prefix uri prefix
     * @param uriFactory decorated {@link UriFactory}
     */
    public PrefixUriFactory(final String prefix, final UriFactory uriFactory) {
        this.prefix = prefix;
        this.uriFactory = uriFactory;
    }


    /** @see UriFactory#buildUri(String, Object[]) */
    public final String buildUri(final String uriKey, final Object... expArgs) {
        return new StringBuilder(this.prefix)
            .append(uriFactory.buildUri(uriKey, expArgs))
            .toString();
    }

}

/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.factory;

/**
 * Implementación de {@link UriFactory} que agrega a un URI generado
 * por otra {@link UriFactory} información de Host o context path.
 * 
 * @author Mariano Cortesi
 * @since Apr 23, 2010
 */
public class AbsoluteUriFactory implements UriFactory {

    private final UriFactory delegate;
    private final String uriPrefix;
    
    /**
     * Creates the AbsoluteUriFactory.
     */
    public AbsoluteUriFactory(final UriFactory delegate, final String uriPrefix) {
        this.delegate = delegate;
        this.uriPrefix = uriPrefix;
    }

    /** @see UriFactory#buildUri(String, Object[]) */
    public final String buildUri(final String uriKey, final Object... expArgs) {
        return new StringBuilder(this.uriPrefix)
            .append(this.delegate.buildUri(uriKey, expArgs))
            .toString();
    }

}

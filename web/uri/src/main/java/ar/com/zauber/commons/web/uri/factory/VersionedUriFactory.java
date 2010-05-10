/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.factory;

/**
 * {@link UriFactory} implementation thats adds a version parameter to 
 * the uri generated by another {@link UriFactory}.
 * 
 * @author Mariano Cortesi
 * @since May 7, 2010
 */
public class VersionedUriFactory implements UriFactory {

    private final String versionParameter;
    private final UriFactory uriFactory;

    /**
     * Creates the VersionedUriFactory.
     *
     * @param version version 
     * @param uriFactory decorated {@link UriFactory}
     */
    public VersionedUriFactory(final String version, final UriFactory uriFactory) {
        this.versionParameter = "v=" + version;
        this.uriFactory = uriFactory;
    }

    /** @see UriFactory#buildUri(String, Object[]) */
    public final String buildUri(final String uriKey, final Object... expArgs) {
        String uri = this.uriFactory.buildUri(uriKey, expArgs);
        int ampIdx = uri.lastIndexOf("?");
        String separator;
        if (ampIdx >= 0) {
            separator = "&";
        } else {
            separator = "?";
        }
        return new StringBuilder(uri)
            .append(separator)
            .append(versionParameter)
            .toString();
    }

}

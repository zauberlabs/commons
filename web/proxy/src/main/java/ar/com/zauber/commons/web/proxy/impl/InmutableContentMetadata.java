/*
 * Copyright (c) 2008 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.proxy.impl;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.proxy.ContentTransformer;

/**
 * Inmutable implementation of ContentMetadata.
 * 
 * @author Alejandro Souto
 * @since 12/11/2008
 */
public class InmutableContentMetadata implements ContentTransformer.ContentMetadata {
    private final String uri;
    private final String contentType;

    public InmutableContentMetadata(final String uri, final String contentType) {
        Validate.notNull(uri);
        // contentType puede ser nulo.
    
        this.uri = uri;
        this.contentType = contentType;
    }

    /** @see ContentMetadata#getUri() */
    public final String getUri() {
        return uri;
    }
        
    /** @see ContentMetadata#getContentType() */
    public final String getContentType() {
        return contentType;
    }
}

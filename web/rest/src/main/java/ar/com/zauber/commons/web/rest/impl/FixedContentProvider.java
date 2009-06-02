/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.rest.impl;

import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.exception.NoSuchEntityException;
import ar.com.zauber.commons.web.rest.ContentProvider;

/**
 * @author Juan F. Codagnone
 * @since Jan 26, 2009
 */
public class FixedContentProvider implements ContentProvider {
    private final Map<URL, String> map;

    /** Creates the FixedContentProvider. */
    public FixedContentProvider(final Map<URL, String> map) {
        Validate.notNull(map);

        this.map = map;
    }

    /** @see ContentProvider#getContent(URL) */
    public final InputStream getContent(final URL url) {
        final String destURL = map.get(url);
        if(destURL == null) {
            throw new NoSuchEntityException(url);
        }

        return getClass().getClassLoader().getResourceAsStream(destURL);
    }

    /** @see ContentProvider#put(URL, InputStream) */
    public final InputStream put(final URL url, final InputStream body) {
        throw new NotImplementedException("won't implement.");
    }

    /** @see ContentProvider#delete(URL) */
    public final void delete(final URL url) {
        throw new NotImplementedException("won't implement.");
    }
}

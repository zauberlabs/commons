/*
 * Copyright (c) 2006 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.syndication.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.syndication.FeedReader;


/**
 * Implementación de {@link FeedReader} que busca el feed en 
 * classpath. Util para testeos. Se carga con el classloader.
 * 
 * @author Juan F. Codagnone
 * @since Jul 2, 2006
 */
public class ClasspathFeedReader implements FeedReader {
    /** path a buscar el archivo */
    private final String path;
    
    /**
     * Creates the ClasspathFeedReader.
     *
     * @param path path of the resource
     */
    public ClasspathFeedReader(final String path) {
        Validate.notNull(path);
        
        this.path = path;
    }
    
    /** @see FeedReader#getReader() */
    public final Reader getReader() throws IOException {
        return new InputStreamReader(getClass().getClassLoader()
                .getResourceAsStream(path));
    }
}

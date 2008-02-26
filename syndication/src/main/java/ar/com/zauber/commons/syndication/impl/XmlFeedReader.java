/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.syndication.impl;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;

import org.apache.commons.lang.Validate;

import com.sun.syndication.io.XmlReader;

import ar.com.zauber.commons.syndication.FeedReader;


/**
 * Implementación de {@link FeedReader} que saca 
 * 
 * @author Juan F. Codagnone
 * @since Jul 2, 2006
 */
public class XmlFeedReader implements FeedReader {
    /** dirección del feed */
    private URL feedURL;
    
    /**
     * Creates the XmlFeedReader.
     * 
     * @param feedURL dirección del feed a sindicar
     */
    public XmlFeedReader(final URL feedURL) {
        Validate.notNull(feedURL);
        
        this.feedURL = feedURL;
    }
    
    /** @see FeedReader#getReader() */
    public final Reader getReader() throws IOException {
        
        return new XmlReader(feedURL);
    }
}

/*
 * Copyright (c) 2005-2008 Zauber S.A. <http://www.zauber.com.ar/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

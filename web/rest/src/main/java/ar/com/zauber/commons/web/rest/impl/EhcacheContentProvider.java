/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
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
package ar.com.zauber.commons.web.rest.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.exception.NoSuchEntityException;
import ar.com.zauber.commons.web.rest.ContentProvider;

/**
 * Cache content provider
 *
 *
 * @author Juan F. Codagnone
 * @since Sep 25, 2009
 */
public class EhcacheContentProvider implements ContentProvider {
    private final ContentProvider contentProvider;
    private final Cache cache;

    /** Creates the EhcacheContentProvider. */
    public EhcacheContentProvider(final ContentProvider contentProvider,
            final Cache cache) {
        Validate.notNull(contentProvider);
        Validate.notNull(cache);
        
        this.contentProvider = contentProvider;
        this.cache = cache;
    }
    
    /** @see ContentProvider#delete(URI) */
    public final void delete(final URI url) {
        cache.remove(url);
        contentProvider.delete(url);
    }

    /** @see ContentProvider#getContent(URI) */
    public final Reader getContent(final URI url) throws NoSuchEntityException {
        final Element e = cache.get(url);
        if(e == null) {
            final Reader is = contentProvider.getContent(url);
            final StringWriter b = new StringWriter();
            try {
                IOUtils.copy(is, b);
                cache.put(new Element(url,  b.toString()));
                return new StringReader(b.toString());
            } catch (IOException ex) {
                throw new UnhandledException(ex);
            } finally {
                IOUtils.closeQuietly(is);
            }
        } else {
            return new StringReader((String) e.getValue());
        }
    }

    /** @see ContentProvider#put(URI, InputStream) */
    public final InputStream put(final URI url, final InputStream body) {
        cache.remove(url);
        return contentProvider.put(url, body);
    }
}

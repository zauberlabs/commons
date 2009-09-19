/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
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

import java.io.InputStream;
import java.net.URI;
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
    private final Map<URI, String> map;

    /** Creates the FixedContentProvider. */
    public FixedContentProvider(final Map<URI, String> map) {
        Validate.notNull(map);

        this.map = map;
    }

    /** @see ContentProvider#getContent(URL) */
    public final InputStream getContent(final URI url) {
        final String destURL = map.get(url);
        if(destURL == null) {
            throw new NoSuchEntityException(url);
        }

        final InputStream is = getClass().getClassLoader().getResourceAsStream(
                destURL);
        if(is == null) {
            throw new NoSuchEntityException(url);
        }
        
        return is;
    }

    /** @see ContentProvider#put(URL, InputStream) */
    public final InputStream put(final URI url, final InputStream body) {
        throw new NotImplementedException("won't implement.");
    }

    /** @see ContentProvider#delete(URL) */
    public final void delete(final URI url) {
        throw new NotImplementedException("won't implement.");
    }
}

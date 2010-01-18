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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import ar.com.zauber.commons.dao.exception.NoSuchEntityException;
import ar.com.zauber.commons.web.rest.ContentProvider;


/**
 * Tests {@link FixedContentProvider}.
 * 
 * @author Juan F. Codagnone
 * @since Sep 12, 2009
 */
public class FixedContentProviderTest {
    private final ContentProvider contentProvider;
    
    /** Creates the FixedContentProviderTest. */
    public FixedContentProviderTest() throws URISyntaxException {
        final Map<URI, String> map = new HashMap<URI, String>();
        map.put(new URI("http://foo"), 
                "ar/com/zauber/commons/web/rest/impl/foo.txt");
        map.put(new URI("http://bar"), "bar.txt");
        contentProvider = new FixedContentProvider(map);
    }
    
    /** test */
    @Test
    public final void testFound() throws URISyntaxException  {
        Assert.assertNotNull(contentProvider.getContent(new URI("http://foo")));
    }
    
    /** test */
    @Test
    public final void testNotFound() throws URISyntaxException {
        final URI url = new URI("http://bar");
        try {
            contentProvider.getContent(url);
            Assert.fail();
        } catch(final NoSuchEntityException e) {
            // ok
        }
    }
}

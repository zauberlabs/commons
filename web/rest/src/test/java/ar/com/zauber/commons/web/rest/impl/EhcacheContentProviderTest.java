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
import java.net.URI;
import java.net.URISyntaxException;

import net.sf.ehcache.CacheManager;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.NotImplementedException;
import org.junit.Assert;
import org.junit.Test;

import ar.com.zauber.commons.dao.exception.NoSuchEntityException;
import ar.com.zauber.commons.web.rest.ContentProvider;


/**
 * {@link EhcacheContentProvider} tests
 *
 *
 * @author Juan F. Codagnone
 * @since Sep 26, 2009
 */
public class EhcacheContentProviderTest {

    @Test
    public final void foo() throws URISyntaxException, IOException {
        final CacheManager manager = new CacheManager(
                getClass().getResourceAsStream("ehcache.xml"));
        Assert.assertNotNull(manager);
        final ContentProvider provider = 
            new EhcacheContentProvider(new ContentProvider() {
            private int i = 0;
            public InputStream put(final URI url, final InputStream body) {
                throw new NotImplementedException();
            }

            public void delete(final URI url) {
                throw new NotImplementedException();
            }

            public Reader getContent(final URI url) throws NoSuchEntityException {
                i++;
                if(i == 2) {
                    Assert.fail();
                }
                return new StringReader("hola!");
            }
        }, manager.getCache("false"));
        
        final String e  = "hola!";
        final URI uri = new URI("http://localhost");
        Assert.assertEquals(e,  IOUtils.toString(provider.getContent(uri)));
        Assert.assertEquals(e,  IOUtils.toString(provider.getContent(uri)));
        Assert.assertEquals(e,  IOUtils.toString(provider.getContent(uri)));
        Assert.assertEquals(e,  IOUtils.toString(provider.getContent(uri)));
        Assert.assertEquals(e,  IOUtils.toString(provider.getContent(uri)));
        Assert.assertEquals(e,  IOUtils.toString(provider.getContent(uri)));
        Assert.assertEquals(e,  IOUtils.toString(provider.getContent(uri)));
        
    }
}

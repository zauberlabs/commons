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
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Assert;
import org.junit.Test;

import ar.com.zauber.commons.dao.exception.NoSuchEntityException;
import ar.com.zauber.commons.web.rest.ContentProvider;


/**
 * TODO Descripcion de la clase. Los comentarios van en castellano.
 * 
 * 
 * @author Mat�as G. Tito
 * @since Oct 23, 2009
 */
public class HttpClientContentProviderTestDriver {
    
    private final ContentProvider cp;
    
    /**
     * Creates the HttpClientContentProviderTestDriver.
     * @throws NoSuchAlgorithmException 
     *
     */
    public HttpClientContentProviderTestDriver() throws NoSuchAlgorithmException {
        
        HttpClient httpClient = new DefaultHttpClient();
        SSLSocketFactory sf = new SSLSocketFactory(SSLContext.getInstance("TLS"));
        sf.setHostnameVerifier(SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

        ((AbstractHttpClient) httpClient).getCredentialsProvider().setCredentials(
                new AuthScope("labs.zauber.com.ar", 443), 
                new UsernamePasswordCredentials(System.getProperty("usuario"), 
                        System.getProperty("password")));
        cp = new HttpClientContentProvider(httpClient);
    }
    
    /** test */
    @Test
    public final void testFound() throws URISyntaxException  {
        Assert.assertNotNull(cp.getContent(new URI("https://labs.zauber.com.ar/crono/0.4/bin/login/")));
    }
    
    /** test */
    @Test
    public final void testNotFound() throws URISyntaxException {
        final URI url = new URI("http://www.google.com.ar/asdasdasd");
        try {
            cp.getContent(url);
            Assert.fail();
        } catch(final NoSuchEntityException e) {
            // ok
        }
    }
}

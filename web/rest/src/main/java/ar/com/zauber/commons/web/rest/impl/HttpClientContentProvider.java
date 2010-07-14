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
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.nio.charset.Charset;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import ar.com.zauber.commons.dao.exception.NoSuchEntityException;
import ar.com.zauber.commons.web.rest.ContentProvider;

/**
 * TODO Descripcion de la clase. Los comentarios van en castellano.
 * 
 * 
 * @author Mat�as G. Tito
 * @since Oct 23, 2009
 * @deprecated Use 
 *   leviathan's ar.com.zauber.leviathan.impl.httpclient.HTTPClientURIFetcher
 */
@Deprecated
public class HttpClientContentProvider extends AbstractContentProvider {
    private final HttpClient httpClient;

    /** constructor */
    public HttpClientContentProvider(final HttpClient httpClient) {
        Validate.notNull(httpClient);
        
        this.httpClient = httpClient;
        
    }
    /** @see ContentProvider#delete(URI) */
    public final void delete(final URI url) {
        throw new NotImplementedException();
        
    }

    /** @see ContentProvider#getContent(URI) */
    public final Reader getContent(final URI url) throws NoSuchEntityException {
        Validate.notNull(url);
        
        final HttpGet get = new HttpGet(url);        
        try {
            final HttpResponse response = httpClient.execute(get);
            final HttpEntity entity = response.getEntity();
            
            final StatusLine statusline = response.getStatusLine();
            handleCode(statusline.getStatusCode(), statusline.getReasonPhrase(), 
                    url);
            
            return new InputStreamReader(entity.getContent(), 
                    getCharset(entity));
        } catch (final ClientProtocolException e) {
            throw new UnhandledException(e);
        } catch (final IOException e) {
            throw new UnhandledException(e);
        }
    }  

    /** the charset */
    private Charset getCharset(final HttpEntity entity) {
        Charset ret = Charset.defaultCharset();

        if(entity.getContentType() != null) {
            String contentType = entity.getContentType().getValue();
            if(contentType != null) {
                for(String s : contentType.split(";")) {
                    s = s.trim();
                    if(s.startsWith("charset=")) {
                        ret = Charset.forName(s.substring(8));
                    }
                }
            }
        }
        return ret;
    }

    /** @see ContentProvider#put(URI, InputStream) */
    public final InputStream put(final URI url, final InputStream body) {
        throw new NotImplementedException();
    }

}

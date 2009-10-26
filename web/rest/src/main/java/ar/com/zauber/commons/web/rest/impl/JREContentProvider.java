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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.UnhandledException;

import ar.com.zauber.commons.web.rest.ContentProvider;

/**
 * {@link ContentProvider} that uses the {@link URL#openConnection()}
 *
 *
 * @author Juan F. Codagnone
 * @since Sep 25, 2009
 */
public class JREContentProvider extends AbstractContentProvider { 
    
    
    /** @see ContentProvider#delete(URI) */
    public final void delete(final URI url) {
        throw new NotImplementedException();
    }

    /** @see ContentProvider#getContent(URI) */
    public final Reader getContent(final URI url) {
        try {
            final URLConnection uc = url.toURL().openConnection();
            final HttpURLConnection huc = uc instanceof HttpURLConnection 
                                        ? (HttpURLConnection) uc : null;
                                        
            uc.setDoInput(true);
            uc.setAllowUserInteraction(true);            
            uc.setRequestProperty("User-Agent", getUserAgent());
            modifyURLConnection(url, huc);
            final String encoding = uc.getContentEncoding();
            if(huc != null) {
                huc.setInstanceFollowRedirects(true);
            }
            uc.connect();
                
            final Charset charset = getCharset(uc);
            if(huc != null) {
                int code = huc.getResponseCode();
                handleCode(code, huc.getResponseMessage(), url);
            }
            return new InputStreamReader(uc.getInputStream(), charset);
        } catch (final MalformedURLException e) {
            throw new UnhandledException(e);
        } catch (final IOException e) {
            throw new UnhandledException(e);
        }
    }

    /** Override to configure a huc */
    protected void modifyURLConnection(final URI url, final HttpURLConnection huc) {
        // template method
    }

    /** the charset */
    private Charset getCharset(final URLConnection uc) {
        Charset ret = Charset.defaultCharset();
        
        String contentType = uc.getContentType();
        if(contentType != null) {
            for(String s : contentType.split(";")) {
                s = s.trim();
                if(s.startsWith("charset=")) {
                    ret = Charset.forName(s.substring(8));
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

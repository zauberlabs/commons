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

import ar.com.zauber.commons.dao.exception.NoSuchEntityException;
import ar.com.zauber.commons.web.rest.ContentProvider;

/**
 * {@link ContentProvider} that uses the {@link URL#openConnection()}
 *
 *
 * @author Juan F. Codagnone
 * @since Sep 25, 2009
 */
public class JREContentProvider implements ContentProvider {
    private String userAgent;
    
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
            uc.setRequestProperty("User-Agent", userAgent);
                        final String encoding = uc.getContentEncoding();
            if(huc != null) {
                huc.setInstanceFollowRedirects(true);
            }
            uc.connect();
                
            final Charset charset = getCharset(uc);
            if(huc != null) {
                int code = huc.getResponseCode();
                if(code >= 200 && code < 300) {
                    // ok
                } else  if(code == 400) {
                    throw new IllegalArgumentException(huc.getResponseMessage());
                } else if(code == 404) {
                    throw new NoSuchEntityException(url);
                } else if(code == 405) {
                    throw new UnsupportedOperationException(
                            huc.getResponseMessage());
                } else if(code == 501) {
                    throw new NotImplementedException(huc.getResponseMessage());
                } else {
                    throw new IllegalStateException("Code " + code + ": "
                            + huc.getResponseMessage());
                }
                
            }
            return new InputStreamReader(uc.getInputStream(), charset);
        } catch (final MalformedURLException e) {
            throw new UnhandledException(e);
        } catch (final IOException e) {
            throw new UnhandledException(e);
        }
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

    public String getUserAgent() {
        return userAgent;
    }

    public final void setUserAgent(final String userAgent) {
        this.userAgent = userAgent;
    }
    
}

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
package ar.com.zauber.commons.web.proxy;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

import junit.framework.TestCase;

/**
 * Tests {@link HttpClientRequestProxy}.
 * 
 * 
 * @author Matías Tito
 * @since Oct 29, 2008
 */
public class HttpClientRequestProxyTest extends TestCase {

    /** test */
    public final void testNoEncoding() {
        HttpClientRequestProxy p = new HttpClientRequestProxy(
                new URLRequestMapper()  {
            public final URLResult getProxiedURLFromRequest(
                    final HttpServletRequest request) {
                return null;
            }
        }, new HttpClient());
        
        HttpMethod m = new GetMethod() {
            /** @see HttpMethodBase#getResponseHeader(String) */
            @Override
            public Header getResponseHeader(final String headerName) {
                return null;
            }
        };
        assertNull(p.getContentType(m));
    }
    
    
    /** test */
    public final void testContentTypeCharset() {
        HttpClientRequestProxy p = new HttpClientRequestProxy(
                new URLRequestMapper()  {
            public final URLResult getProxiedURLFromRequest(
                    final HttpServletRequest request) {
                return null;
            }
        }, new HttpClient());
        
        HttpMethod m = new GetMethod() {
            /** @see HttpMethodBase#getResponseHeader(String) */
            @Override
            public Header getResponseHeader(final String headerName) {
                return new Header(headerName, "text/html ; charset =  utf-8");
            }
        };
        assertEquals("text/html ; charset =  utf-8", p.getContentType(m));
    }
}

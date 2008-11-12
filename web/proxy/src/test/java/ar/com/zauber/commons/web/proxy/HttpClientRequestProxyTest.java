/*
 * Copyright (c) 2008 Zauber S.A.  -- All rights reserved
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

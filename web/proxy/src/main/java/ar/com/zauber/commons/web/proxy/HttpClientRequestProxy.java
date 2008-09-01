/*
 * Copyright (c) 2005-2008 Zauber S.A. <http://www.zauber.com.ar/>
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

import java.io.InputStream;
import java.net.ConnectException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.proxy.transformers.NullContentTransformer;

/**
 * Proxy requests
 * 
 * @author Juan F. Codagnone
 * @since Sep 1, 2008
 */
public class HttpClientRequestProxy {
    private final URLRequestMapper urlRequestMapper;
    private final HttpClient httpClient;
    private final ContentTransformer contentTransformer;
    private final List<String> forbiddenHeader;
    private static List<String> defaultForbiddenHeader = 
        Arrays.asList(new String[] {
                "Set-Cookie", 
                "Server",
                "X-Cache", 
                "X-Cache-Lookup", 
             });
    /** Creates the RequestProxy. */
    public HttpClientRequestProxy(final URLRequestMapper urlRequestMapper,
            final HttpClient httpClient) {
        this(urlRequestMapper, httpClient, new NullContentTransformer(),
                defaultForbiddenHeader);
    }
    
    /** Creates the RequestProxy. */
    public HttpClientRequestProxy(final URLRequestMapper urlRequestMapper,
            final HttpClient httpClient, 
            final ContentTransformer contentTransformer) {
        this(urlRequestMapper, httpClient, contentTransformer,
                defaultForbiddenHeader);
    }
    
    /** Creates the RequestProxy. */
    public HttpClientRequestProxy(final URLRequestMapper urlRequestMapper,
            final HttpClient httpClient, final List<String> forbiddenHeader) {
        this(urlRequestMapper, httpClient, new NullContentTransformer(),
                forbiddenHeader);
    }
    
    /** Creates the RequestProxy. */
    public HttpClientRequestProxy(final URLRequestMapper urlRequestMapper,
            final HttpClient httpClient,
            final ContentTransformer contentTransformer,
            final List<String> forbiddenHeader) {
        Validate.notNull(urlRequestMapper);
        Validate.notNull(httpClient);
        Validate.notNull(contentTransformer);
        Validate.noNullElements(forbiddenHeader);

        this.urlRequestMapper = urlRequestMapper;
        this.httpClient = httpClient;
        this.contentTransformer = contentTransformer;
        
        
        if(contentTransformer.getContentType() != null) {
            forbiddenHeader.add("Content-Type");
        }
        this.forbiddenHeader = forbiddenHeader;
    }

    /**
     * @see AbstractController#handleRequestInternal(HttpServletRequest,
     *      HttpServletResponse)
     */
    public final void handleRequestInternal(final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {

        final URLResult r = urlRequestMapper.getProxiedURLFromRequest(request);
        if (r.hasResult()) {
            final GetMethod method = buildRequest(request, r);
            InputStream is = null;
            try {
                final int code = httpClient.executeMethod(method);
                response.setStatus(code);
                proxyHeaders(response, method);
                addOtherHeaders(response, method);
                is = method.getResponseBodyAsStream(); 
                
                if(contentTransformer.getContentType() != null) {
                    response.setContentType(contentTransformer.getContentType()); 
                }
                
                try {
                    contentTransformer.transform(is, response.getOutputStream());
                } finally {
                   is.close();
                }
            } catch(final ConnectException e) {
                response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE,
                        e.getMessage());
            } finally {
                if(is != null) {
                    is.close();
                }
                method.releaseConnection();
            }
        } else {
            response.sendError(404, "no mapping for this url");
        }
    }

    /**
     * @param request
     * @return
     */
    private GetMethod buildRequest(final HttpServletRequest request,
            final URLResult urlResult) {
        final String method = request.getMethod().toUpperCase();
        final GetMethod ret;

        if ("GET".equals(method)) {
            ret = new GetMethod(urlResult.getURL().toExternalForm());
        } else {
            throw new NotImplementedException("i accept patches :)");
        }

        return ret;
    }

    /**
     * Metodo para overraidear.
     * 
     * La idea de este metodo es que agrege headers a la respuesta.
     */
    protected void addOtherHeaders(final HttpServletResponse response,
            final HttpMethod method) {
        // nada que hacer
    }

    /**
     * Pasa los headers de un request a otro. Copia todos salvo algunos
     * prohibidos que no tienen sentido.
     */
    private void proxyHeaders(final HttpServletResponse response,
            final HttpMethod method) {
        final Header []headers = method.getResponseHeaders();

        for(final Header header : headers) {
            boolean skip = false;
            for (final String forbidden : forbiddenHeader) {
                skip = header.getName().equals(forbidden);
                if (skip) {
                    break;
                }
            }

            if (!skip) {
                response.setHeader(header.getName(), header.getValue());
            }
        }
    }
}

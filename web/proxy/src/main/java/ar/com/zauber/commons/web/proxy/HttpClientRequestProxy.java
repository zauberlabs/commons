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

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.UnhandledException;
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
    private final List<String> forbiddenHeader = new ArrayList<String>();
    private static List<String> defaultForbiddenHeader = 
        Arrays.asList(new String[] {
                "Set-Cookie", 
                "Server",
                "X-Cache", 
                "X-Cache-Lookup", 
                "Transfer-Encoding", 
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
        for(final String f : forbiddenHeader) {
            this.forbiddenHeader.add(f.toLowerCase());
        }
    }

    /**
     * @see AbstractController#handleRequestInternal(HttpServletRequest,
     *      HttpServletResponse)
     *      @throws Exception .
     */
    public final void handleRequestInternal(final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {

        final URLResult r = urlRequestMapper.getProxiedURLFromRequest(request);
        if (r.hasResult()) {
            final HttpMethod method = buildRequest(request, r);
            InputStream is = null;
            try {
                httpClient.executeMethod(method);
                updateResponseCode(request, response, method);
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
                onConnectionException(request, response, method, e);
            } finally {
                if(is != null) {
                    is.close();
                }
                method.releaseConnection();
            }
        } else {
            onNoMapping(request, response);
        }
    }

    // CHECKSTYLE:DESIGN:OFF
    /**
     * Método que se puede overridear en el caso de necesitar otro comportamiento al
     * no encontrarse un mapeo apropiado para la url recibida.
     * 
     * @param request
     * @param response
     * @throws Exception .
     */
    protected void onNoMapping(final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        response.sendError(404, "no mapping for this url");
    }
    // CHECKSTYLE:DESIGN::ON
    
    // CHECKSTYLE:DESIGN:OFF
    /**
     * Método que se puede overridear en el caso de necesitar otro comportamiento al
     * producirse un error de conexión.
     * 
     * @param request
     * @param response
     * @param method
     * @param e
     * @throws Exception .
     */
    protected void onConnectionException(final HttpServletRequest request,
            final HttpServletResponse response, final HttpMethod method,
            final ConnectException e) throws Exception {
        response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE,
                e.getMessage());
    }
    // CHECKSTYLE:DESIGN::ON
    
    /**
     * Método ideado para overriding en caso de necesitar realizar logs, o modificar
     * el response code. Para transformaciones del body, agregar un transformer. 
     * 
     * @param request
     * @param response
     * @param method
     * @throws IOException .
     */
    // CHECKSTYLE:DESIGN:OFF
    protected void updateResponseCode(final HttpServletRequest request,
            final HttpServletResponse response,
            final HttpMethod method) throws IOException {

        response.setStatus(method.getStatusCode());
    }
    // CHECKSTYLE:DESIGN::ON

    /**
     * @param request
     * @return
     */
    private HttpMethod buildRequest(final HttpServletRequest request,
            final URLResult urlResult) {
        final String method = request.getMethod().toUpperCase();
        final HttpMethod ret;

        if ("GET".equals(method)) {
            ret = new GetMethod(urlResult.getURL().toExternalForm());
        } else if("POST".equals(method)) {
            PostMethod pm = new PostMethod(urlResult.getURL().toExternalForm());
            proxyHeaders(request, pm);            
            try {
                pm.setRequestEntity(new InputStreamRequestEntity(
                        request.getInputStream()));
            } catch (IOException e) {
                throw new UnhandledException("No pudo abrirse el InputStream"
                        + "del request.", e);
            }
            ret = pm;
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
                skip = header.getName().toLowerCase().equals(forbidden);
                if (skip) {
                    break;
                }
            }

            if (!skip) {
                response.setHeader(header.getName(), header.getValue());
            }
        }
    }
    
    /**
     * Pasa los headers de un request a otro. Copia todos salvo algunos
     * prohibidos que no tienen sentido.
     */
    private void proxyHeaders(final HttpServletRequest request,
            final PostMethod method) {

        Enumeration<String> names = request.getHeaderNames(); 

        while(names.hasMoreElements()) {
            String name = names.nextElement().toLowerCase();
            Enumeration<String> headers = request.getHeaders(name);
            if(!forbiddenHeader.contains(name)) {
                while(headers.hasMoreElements()) {
                    method.addRequestHeader(name, headers.nextElement());
                    
                }    
            }
        }
    }
}

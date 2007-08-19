/*
 * Copyright (c) 2006 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.spring.web.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.codehaus.plexus.util.IOUtil;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;


/**
 * Base class for controllers that are a proxy (they foward the request
 * to another http server
 * 
 * @author Juan F. Codagnone
 * @since Feb 24, 2006
 */
public abstract class AbstractProxyController extends AbstractController {
    /** url del servicio a proxificar */
    private final URL baseUrl;
    /** user-agent */
    private final String userAgent;
    /** cookie a usar cuando nos conectamos al servicio original */
    private String cookie;
    /** transformers */
    private final OutputTransformer transformer;
    
    private static String []defaultForbiddenHeader = {
        "Set-Cookie",
        "Server",
        "X-Cache",
        "X-Cache-Lookup",
    };
    
    private final String []forbiddenHeader;
    
    public AbstractProxyController(final URL baseUrl,
            final String userAgent) {
        this(baseUrl, userAgent, new NullOutputTransformer());
    }
    
    /**
     * Creates the GeocoderProxyController.
     *
     * @param baseUrl url del servicio de geocoding
     */
    public AbstractProxyController(final URL baseUrl,
            final String userAgent, 
            final OutputTransformer outputTransformer) {
        Validate.notNull(baseUrl);
        Validate.notNull(userAgent);
        Validate.notNull(outputTransformer);
        
        this.baseUrl = baseUrl;
        this.userAgent = userAgent;
        this.transformer = outputTransformer;
        
        final List<String> h = new ArrayList<String>();
        for(final String header : defaultForbiddenHeader) {
            h.add(header);
        }
        if(transformer.getContentType() != null) {
            h.add("Content-Type");
        }
        forbiddenHeader = h.toArray(new String[]{});
    }

    /**
     * @param request http request
     * @return the destination url for this request
     * @throws Exception on error
     */
    protected abstract URL getURL(final HttpServletRequest request, 
            final HttpServletResponse response)
            throws Exception;
    
    /**
     * @see AbstractController#handleRequestInternal(HttpServletRequest, 
     *                                               HttpServletResponse)
     */
    protected final ModelAndView handleRequestInternal(
            final HttpServletRequest request, 
            final HttpServletResponse response) throws Exception {

           final URL url = getURL(request, response);
           if(url != null) {
               final URLConnection uc = url.openConnection();
               if(uc instanceof HttpURLConnection) {
                   final HttpURLConnection huc = (HttpURLConnection) uc;
                   huc.setAllowUserInteraction(true);
                   huc.setRequestProperty("User-Agent", userAgent);
                   huc.setRequestMethod("GET");
                   if(cookie != null) {
                       huc.setRequestProperty("Cookie", cookie);
                   }
                   
                   // mantenemos la sesion en el cliente
                   final String requestCookie = huc.getHeaderField("Set-Cookie"); 
                   if(requestCookie != null) {
                      if(!requestCookie.equals(cookie)) {
                          cookie = requestCookie;
                      }
                   }
                   proxyHeaders(response, huc);
                   addOtherHeaders(response, huc);
               }
               
               final InputStream is = uc.getInputStream();
               if(transformer.getContentType() != null) {
                   response.setContentType(transformer.getContentType()); 
               }
               
               try {
                   transformer.transform(is, response.getOutputStream());
               } finally {
                   try {
                      is.close();
                   } catch(Exception e) {
                   }
               }
           }
           
           return null;
       }

    /**
     * Metodo para overraidear. 
     * 
     * La idea de este metodo es que agrege headers a la respuesta.
     * 
     * @param response response
     * @param huc  original 
     */
    protected void addOtherHeaders(final HttpServletResponse response, 
            final HttpURLConnection huc) {
        // nada que hacer
    }

    /**
     * Pasa los headers de un request a otro. Copia todos salvo algunos
     * prohibidos que no tienen sentido. 
     *  
     * @param response final response
     * @param huc input response
     */
    private void proxyHeaders(final HttpServletResponse response, HttpURLConnection huc) {
        String s;
           for(int i = 1; (s = huc.getHeaderFieldKey(i)) != null; i++) {
               final String value = huc.getHeaderField(i);
               boolean skip = false;
               for(final String forbidden : forbiddenHeader) {
                   skip = s.equals(forbidden);
                   if(skip) {
                       break;
                   }
               }
               
               if(!skip) {
                   response.setHeader(s, value);
               }
           }
    }

    /**
     * @return true si el header no se debe "proxificar".
     */
    protected boolean skipHeader(String header) {
        boolean skip = false;
        for(final String forbidden : forbiddenHeader) {
            skip = header.equals(forbidden);
            if(skip) {
                break;
            }
        }
        
        return skip;
    }
    
    /**
     * Returns the baseUrl.
     * 
     * @return <code>URL</code> with the baseUrl.
     */
    protected final URL getBaseUrl() {
        return baseUrl;
    }
    
    public static interface OutputTransformer {
        String getContentType();
        void transform(InputStream is, OutputStream os);
    }
}

/**
 * 
 * Implementacion nula de transformador de salida. Copia lo que viene
 * del target hacia la salida.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Aug 7, 2007
 */
class NullOutputTransformer implements AbstractProxyController.OutputTransformer {

    /** @see OutputTransformer#getContentType() */
    public String getContentType() {
        return null;
    }

    /** @see OutputTransformer#transform(InputStream, OutputStream) */
    public void transform(final InputStream is, final OutputStream os) {
        try {
            IOUtil.copy(is, os);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
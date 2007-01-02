/*
 * Copyright (c) 2006 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.spring.web.controllers;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
    
    private static String []forbiddenHeader = {
        "Set-Cookie",
        "Server",
        "X-Cache",
        "X-Cache-Lookup",
    };
    
    /**
     * Creates the GeocoderProxyController.
     *
     * @param baseUrl url del servicio de geocoding
     */
    public AbstractProxyController(final URL baseUrl,
            final String userAgent) {
        Validate.notNull(baseUrl);
        Validate.notNull(userAgent);
        
        this.baseUrl = baseUrl;
        this.userAgent = userAgent;
    }

    /**
     * @param request http request
     * @return the destination url for this request
     * @throws Exception on error
     */
    protected abstract URL getURL(final HttpServletRequest request)
            throws Exception;
    
    /**
     * @see AbstractController#handleRequestInternal(HttpServletRequest, 
     *                                               HttpServletResponse)
     */
    protected final ModelAndView handleRequestInternal(
            final HttpServletRequest request, 
            final HttpServletResponse response) throws Exception {

           final URL url = getURL(request);
           HttpURLConnection huc = (HttpURLConnection)url.openConnection();
           huc.setAllowUserInteraction(true);
           huc.setRequestProperty("User-Agent", userAgent);
           huc.setRequestMethod("GET");
           if(cookie != null) {
               huc.setRequestProperty("Cookie", cookie);
           }
           InputStream is = huc.getInputStream();
         
           // mantenemos la sesion en el cliente
           final String requestCookie = huc.getHeaderField("Set-Cookie"); 
           if(requestCookie != null) {
              if(!requestCookie.equals(cookie)) {
                  cookie = requestCookie;
              }
           }
           
           proxyHeaders(response, huc);
           addOtherHeaders(response, huc);
           
           // response.setContentType(huc.getContentType());
           try {
               IOUtil.copy(is, response.getOutputStream());
           } finally {
               is.close();
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
}

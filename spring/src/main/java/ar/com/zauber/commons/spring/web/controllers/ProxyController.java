/*
 * Copyright (c) 2008 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.spring.web.controllers;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.proxy.URLRequestMapper;

/**
 * AbstractProxyController that uses an {@link URLRequestMapper} to get
 * the destination URL.
 * 
 * @author Juan F. Codagnone
 * @since Aug 28, 2008
 */
public class ProxyController extends AbstractProxyController {
    private final URLRequestMapper urlRequestMapper;

    /** constructor */
    public ProxyController(final String userAgent,
            final URLRequestMapper urlRequestMapper) throws MalformedURLException {
        super(new URL("http://127.0.0.1"), userAgent);
        
        Validate.notNull(urlRequestMapper);
        this.urlRequestMapper = urlRequestMapper;
    }

    /** @see AbstractProxyController#getURL(HttpServletRequest,
     *  HttpServletResponse)
     *  @throws Exception on error  
     **/
    @Override
    public final URL getURL(final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        return urlRequestMapper.getProxiedURLFromRequest(request);
    }
}

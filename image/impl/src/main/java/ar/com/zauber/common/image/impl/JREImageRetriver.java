/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.common.image.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;

import org.apache.commons.lang.Validate;

import ar.com.zauber.common.image.services.ImageRetriver;


/**
 * Trae imagenes de otros sitios, de forma segura.
 *  
 * @author Juan F. Codagnoe
 * @author Gabriel V. Baños
 * @since 03/07/2006
 */
public class JREImageRetriver implements ImageRetriver {
    /** user agent */
    private String userAgent = null;
    /** time out in seconds */
    private int timeout = 5;
    
    /** ver constructor */
    private final int maxBytes;

    /**
     * Creates the JREImageRetriver.
     * 
     * @param maxBytes cantidad maxima de bytes dispuesto a bajar por archivo
     */
    public JREImageRetriver(final int maxBytes) {
        this.maxBytes = maxBytes;
    }


    public final InputStream retrive(final URL url) 
        throws IOException {
        Validate.notNull(url);
        
        final URLConnection uc = url.openConnection();
        if(uc instanceof HttpURLConnection) {
            final HttpURLConnection huc = (HttpURLConnection)uc;
            prepare(uc);
            huc.connect();
            final String contentType = huc.getContentType().trim();
            if(contentType != null && contentType.length() > 0 && 
                    !huc.getContentType().startsWith("image/")) {
                throw new RuntimeException(
                        "la URL no parece apuntar a una imagen ");
            }
            if(huc.getContentLength() > maxBytes) {
                throw new RuntimeException("la imagen pesa más de "
                        + maxBytes);
            } 
            final InputStream is = uc.getInputStream();
            return is;
        } else {
            throw new IllegalArgumentException("solo se soporta el protocolo "
                    + " http");
        }
    }
    
    /**
     * @param uc {@link URLConnection} a preparar.
     */
    private void prepare(final URLConnection uc) {
        uc.setAllowUserInteraction(false);
        uc.setReadTimeout(timeout * 1000);
        if(userAgent != null) {
            uc.setRequestProperty("User-Agent", userAgent);    
        }
    }

    
    /**
     * Returns the userAgent.
     * 
     * @return <code>String</code> with the userAgent.
     */
    public final String getUserAgent() {
        return userAgent;
    }

    
    /**
     * Sets the userAgent. 
     *
     * @param userAgent <code>String</code> with the userAgent.
     */
    public final void setUserAgent(final String userAgent) {
        this.userAgent = userAgent;
    }

    
    /**
     * Returns the timeout.
     * 
     * @return <code>int</code> with the timeout.
     */
    public final int getTimeout() {
        return timeout;
    }

    
    /**
     * Sets the timeout. 
     *
     * @param timeout <code>int</code> with the timeout.
     */
    public final void setTimeout(final int timeout) {
        Validate.isTrue(timeout > 0);
        this.timeout = timeout;
    }
}

/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.rest.impl;

import java.net.URI;

import org.apache.commons.lang.NotImplementedException;

import ar.com.zauber.commons.dao.exception.NoSuchEntityException;
import ar.com.zauber.commons.web.rest.ContentProvider;

/**
 * Base class for {@link ContentProvider}.
 * 
 * @author Matías G. Tito
 * @since Oct 23, 2009
 */
public abstract class AbstractContentProvider implements ContentProvider {
    private String userAgent;
    

    public String getUserAgent() {
        return userAgent;
    }

    public final void setUserAgent(final String userAgent) {
        this.userAgent = userAgent;
    }
    
    
    protected void handleCode(final int code, final String responseMessage,
            final URI url) {
        if(code >= 200 && code < 300) {
            // ok
        } else  if(code == 400) {
            throw new IllegalArgumentException(responseMessage);
        } else if(code == 404) {
            throw new NoSuchEntityException(url);
        } else if(code == 405) {
            throw new UnsupportedOperationException(
                    responseMessage);
        } else if(code == 501) {
            throw new NotImplementedException(responseMessage);
        } else {
            throw new IllegalStateException("Code " + code + ": "
                    + responseMessage);
        }
    }

}

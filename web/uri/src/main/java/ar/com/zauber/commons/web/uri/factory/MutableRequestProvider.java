/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.factory;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.Validate;

/**
 * Implementacion util para tests
 * 
 * @author Juan F. Codagnone
 * @since Sep 4, 2010
 */
public class MutableRequestProvider implements RequestProvider {
    private HttpServletRequest request;
    
    /** construct */
    public MutableRequestProvider() {
        // void
    }
    
    /** construct */
    public MutableRequestProvider(final HttpServletRequest request) {
        Validate.notNull(request);
        
        this.request = request;
    }
    
    /** @see RequestProvider#getRequest() */
    public final HttpServletRequest getRequest() {
        if(request == null) {
            throw new IllegalStateException("no request set");
        }
        return request;
    }

    public final void setRequest(final HttpServletRequest request) {
        this.request = request;
    }
}

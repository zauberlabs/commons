/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.filter.webkit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 *  Clase que extiende el wrapper de {@link HttpServletRequest} para cambiar el 
 *  Accept de content type a "text/html,application/xhtml+xml,
 *      application/xml;q=0.9,*\/*;q=0.8".
 * @author Mariano Semelman
 * @since Jun 11, 2010
 */
public class WebkitHttpServletRequestWrapper extends HttpServletRequestWrapper {

    /** Creates the WebkitHttpServletRequestWrapper.
     * @param request */
    public WebkitHttpServletRequestWrapper(final HttpServletRequest request) {
        super(request);
    }

    //CHECKSTYLE:ALL:OFF
    private static final String ACCEPT = "Accept";
    private static final String ACCEPT_HEADER = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
    //CHECKSTYLE:ALL:ON
    
    /** @see HttpServletRequestWrapper#getHeader(String) */
    @Override
    public final String getHeader(final String name) {
        if(name != null && name.equals(ACCEPT)) {
            return ACCEPT_HEADER;
        }
        return super.getHeader(name);
    }
    
}

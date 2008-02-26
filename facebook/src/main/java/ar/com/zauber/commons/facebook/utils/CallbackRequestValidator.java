/*
 * Copyright (c) 2007 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.facebook.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Validates callback requests from the server
 * 
 * @author Juan F. Codagnone
 * @since Dec 24, 2007
 */
public interface CallbackRequestValidator {

    /** validates a callback request. 
     * @return <code>true</code> if the request is valid. 
     */
    boolean validate(HttpServletRequest request);
}

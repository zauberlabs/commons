/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.factory;

import javax.servlet.http.HttpServletRequest;

/**
 * Obtiene el request
 * 
 * 
 * @author Juan F. Codagnone
 * @since Sep 4, 2010
 */
public interface RequestProvider {

    /** get current request */
    HttpServletRequest getRequest();
}

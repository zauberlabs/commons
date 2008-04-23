/*
 * Copyright (c) 2008 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.spring.test;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

/**
 * Crea http servlets requests para tests de controladores dada una definición.
 * 
 * @author Juan F. Codagnone
 * @since Apr 22, 2008
 */
public interface HttpServletRequestFactory {

    /** 
     * dada la deficion del request en un stream (cada implementacion sabe
     * cual es su formato, @return un servlet request list para ser usado.
     */
    HttpServletRequest create(InputStream is);
}

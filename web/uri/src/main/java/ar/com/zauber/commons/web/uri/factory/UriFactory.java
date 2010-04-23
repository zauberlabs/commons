/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.factory;

/**
 * Constructor de URIs en forma dinámica.
 * 
 * @author Mariano Cortesi
 * @since Apr 23, 2010
 */
public interface UriFactory {

    /**
     * Construye la uri referida por uriKey con los parametros expArgs.
     * 
     * @param uriKey
     *            Clave del Uri
     * @param expArgs
     *            Parametros de la expresión referida por el uriKey.
     * @return uri generado.
     */
    String buildUri(final String uriKey, final Object... expArgs);

}
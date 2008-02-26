/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.spring.beans.factory.impl;


/**
 * Provee el nombre de la maquina donde se está corriendo
 * 
 * @author Juan F. Codagnone
 * @since Aug 5, 2006
 */
public interface HostnameProvider {

    /** @return el hostname de la maquina donde se está corriendo */
    String getHostname();
}

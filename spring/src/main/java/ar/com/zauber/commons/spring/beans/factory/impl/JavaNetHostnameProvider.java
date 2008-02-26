/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.spring.beans.factory.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;



/**
 * Implementacion de {@see HostnameProvider} que usa java.net.*.
 * 
 * @author Juan F. Codagnone
 * @since Aug 5, 2006
 */
public class JavaNetHostnameProvider implements HostnameProvider {
    /** localhost hostname */
    private final String hostname;
    
    /**
     * Creates the JavaNetHostnameProvider.
     *
     * @throws UnknownHostException si localhost no tiene nombre :(
     */
    public JavaNetHostnameProvider() throws UnknownHostException {
        hostname = InetAddress.getLocalHost().getHostName();
    }
    
    /** @see HostnameProvider#getHostname() */
    public final String getHostname() {
        return hostname;
    }
}

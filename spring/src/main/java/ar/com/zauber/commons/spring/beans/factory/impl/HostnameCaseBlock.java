/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.spring.beans.factory.impl;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.spring.beans.factory.CaseBlock;


/**
 * Implementación de {@link CaseBlock} que permite diferenciar entre
 * hosts segun el hostname de la maquina donde se está ejecutando.
 * 
 * @author Juan F. Codagnone
 * @since Aug 5, 2006
 */
public class HostnameCaseBlock implements CaseBlock {
    /** expected hostnames to match  (||)*/
    private final String []expectedHostNames;
    /** provides the hostname */
    private final HostnameProvider hostnameProvider;
    /** result */
    private Object object;

    public HostnameCaseBlock(final String expectedHostname,
            final HostnameProvider hostnameProvider,
            final Object object) {
        
        this(new String[]{expectedHostname}, hostnameProvider, object);
    }
    
    public HostnameCaseBlock(final String []expectedHostnames,
            final HostnameProvider hostnameProvider,
            final Object object) {
        Validate.notEmpty(expectedHostnames, "expectedHostnames");
        Validate.notNull(hostnameProvider, "hostnameProvider");
        Validate.notNull(object, "factoryBean");
        
        this.expectedHostNames = expectedHostnames;
        this.hostnameProvider = hostnameProvider;
        this.object = object;
    }
    
    /** @see CaseBlock#evaluate() */
    public final boolean evaluate() {
        boolean ret = false;
        
        for(final String hostname : expectedHostNames) {
            ret |= hostnameProvider.getHostname().equals(hostname);
        }
        
        return ret;
    }

    /** @see CaseBlock#getObject() */
    public final Object getObject() {
        return object;
    }
}

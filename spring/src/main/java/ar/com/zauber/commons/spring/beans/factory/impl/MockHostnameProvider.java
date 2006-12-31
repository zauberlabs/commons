/*
 * Copyright (c) 2006 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.spring.beans.factory.impl;

import org.apache.commons.lang.Validate;



/**
 * Implementacion tonta de {@link HostnameProvider} util para testeos
 * 
 * @author Juan F. Codagnone
 * @since Aug 5, 2006
 */
public class MockHostnameProvider implements HostnameProvider {
    /** hostname */
    private final String hostname;

    /**
     * Creates the MockHostnameProvider.
     *
     * @param hostname hostname
     */
    public MockHostnameProvider(final String hostname) {
        Validate.notEmpty(hostname);
        this.hostname = hostname;
    }
   
    /** @see HostnameProvider#getHostname()  */
    public final String getHostname() {
        return hostname;
    }

}

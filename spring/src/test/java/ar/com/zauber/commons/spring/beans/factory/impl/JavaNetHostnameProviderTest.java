/*
 * Copyright (c) 2006 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.spring.beans.factory.impl;

import junit.framework.TestCase;


/**
 * La clase es imposible de testear (cambia de maquina en maquina).
 * Util para detectar fallos.
 * 
 * @author Juan F. Codagnone
 * @since Aug 6, 2006
 */
public class JavaNetHostnameProviderTest extends TestCase {

    /** ehh     */
    public final void testGetHostname() throws Exception {
        System.out.println(new JavaNetHostnameProvider().getHostname());
    }
}

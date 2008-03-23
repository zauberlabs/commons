/*
 * Copyright (c) 2008 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.spring.configurers;

/**
 * Bean de prueba, para probar en el contexto
 * 
 * 
 * @author Juan F. Codagnone
 * @since Mar 23, 2008
 */
public class CheckBean {
    private final int i;

    /** constructor */
    public CheckBean(final int i) {
        this.i = i;
    }

    public final int getI() {
        return i;
    }
    
}

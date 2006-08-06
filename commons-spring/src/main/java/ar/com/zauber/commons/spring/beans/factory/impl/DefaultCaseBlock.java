/*
 * Copyright (c) 2006 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.spring.beans.factory.impl;

import ar.com.zauber.commons.spring.beans.factory.CaseBlock;


/**
 * Default block, para poner al final del switch.
 * 
 * @author Juan F. Codagnone
 * @since Aug 6, 2006
 */
public class DefaultCaseBlock implements CaseBlock {
    /** objeto a retornar */
    private Object object;

    public DefaultCaseBlock(Object object) {
        this.object = object;
        
    }
    /** @see ar.com.zauber.commons.spring.beans.factory.CaseBlock#evaluate() */
    public final boolean evaluate() {
        return true;
    }

    /** @see CaseBlock#getObject() */
    public final Object getObject() {
        return object;
    }
}

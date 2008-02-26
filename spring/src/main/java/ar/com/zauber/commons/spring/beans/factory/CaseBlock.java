/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.spring.beans.factory;


/**
 * es parte de {@link SwitchConditianalFactoryBean}
 * 
 * @author Juan F. Codagnone
 * @since Aug 5, 2006
 */
public interface CaseBlock {

    /** evalua la condicion del case*/
    boolean evaluate();
    
    /** objeto a retornar si {@link #evaluate()} es <code>true</code> */
    Object getObject();
}

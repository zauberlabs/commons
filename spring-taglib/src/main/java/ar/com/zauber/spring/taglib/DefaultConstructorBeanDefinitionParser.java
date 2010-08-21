/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.spring.taglib;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

import ar.com.zauber.commons.dao.closure.NullClosure;

/**
 * TODO Descripcion de la clase. Los comenterios van en castellano.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Aug 21, 2010
 */
public class DefaultConstructorBeanDefinitionParser    
     extends AbstractSingleBeanDefinitionParser {
    private final Class<?> clazz;

    /**
     * Creates the DefaultConstructorBeanDefinitionParser.
     *
     */
    public DefaultConstructorBeanDefinitionParser(final Class<?> clazz) {
        Validate.notNull(clazz);
        
        this.clazz = clazz;
    }
    
    /** @see AbstractSingleBeanDefinitionParser#getBeanClass(Element) */
    @Override
    protected final Class<?> getBeanClass(final Element element) {
        return clazz;
    }

}

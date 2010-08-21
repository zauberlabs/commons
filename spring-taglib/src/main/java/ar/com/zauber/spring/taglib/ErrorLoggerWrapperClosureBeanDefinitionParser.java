/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.spring.taglib;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import ar.com.zauber.commons.dao.closure.ErrorLoggerWrapperClosure;

/**
 * Creates ErrorLoggerWrapperClosure
 * 
 * 
 * @author Juan F. Codagnone
 * @since Aug 21, 2010
 */
public class ErrorLoggerWrapperClosureBeanDefinitionParser 
     extends AbstractSingleBeanDefinitionParser {
    
    /** @see AbstractSingleBeanDefinitionParser#getBeanClass(Element) */
    @Override
    protected final Class<?> getBeanClass(final Element element) {
        return ErrorLoggerWrapperClosure.class;
    }

    /** @see AbstractSingleBeanDefinitionParser#doParse(Element, ParserContext, 
     *  BeanDefinitionBuilder) */
    @Override
    protected final void doParse(final Element e, final ParserContext parserContext, 
            final BeanDefinitionBuilder builder) {
        
        builder.addConstructorArgValue(parserContext.getDelegate().parseListElement(
                e, builder.getBeanDefinition()).iterator().next());
    }
}

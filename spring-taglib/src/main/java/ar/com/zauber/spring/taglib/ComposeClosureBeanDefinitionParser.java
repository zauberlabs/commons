/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.spring.taglib;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import ar.com.zauber.commons.dao.closure.ComposeClosure;

/**
 * Creates {@link ComposeClosure}
 * 
 * 
 * @author Juan F. Codagnone
 * @since Aug 20, 2010
 */
public class ComposeClosureBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
    /** @see AbstractSingleBeanDefinitionParser#getBeanClass(Element) */
    @Override
    protected final Class<?> getBeanClass(final Element element) {
        return ComposeClosure.class;
    }
    
    @Override
    protected final void doParse(final Element e,
            final ParserContext parserContext,
            final BeanDefinitionBuilder builder) {

        builder.addConstructorArgValue(parserContext.getDelegate().parseListElement(
                e, builder.getBeanDefinition()));
    }

}

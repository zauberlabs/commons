/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.spring.taglib;

import java.util.List;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import ar.com.zauber.commons.dao.closure.MutableClosure;

/**
 * TODO Descripcion de la clase. Los comenterios van en castellano.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Aug 21, 2010
 */
public class MutableClosureBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
    /** @see AbstractSingleBeanDefinitionParser#getBeanClass(Element) */
    @Override
    protected final Class<?> getBeanClass(final Element element) {
        return MutableClosure.class;
    }

    /** @see AbstractSingleBeanDefinitionParser#doParse(Element, ParserContext, 
     *  BeanDefinitionBuilder) */
    @Override
    protected final void doParse(final Element e, final ParserContext parserContext, 
            final BeanDefinitionBuilder builder) {
        final List<?> l = parserContext.getDelegate().parseListElement(
                e, builder.getBeanDefinition());
        
        if(!l.isEmpty()) {
            builder.addConstructorArgValue(l.iterator().next());
        }
    }

}

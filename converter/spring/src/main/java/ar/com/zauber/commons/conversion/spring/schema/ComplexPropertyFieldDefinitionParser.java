/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.conversion.spring.schema;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import ar.com.zauber.commons.conversion.config.ConversionField;

/**
 * Parses a complex property ConversionField definition
 * from the application context's XML.
 * 
 * 
 * @author Juan Edi
 * @since Nov 17, 2009
 */
public class ComplexPropertyFieldDefinitionParser extends
        AbstractSimpleBeanDefinitionParser implements BeanDefinitionParser {

    /** @see AbstractSingleBeanDefinitionParser#getBeanClass(Element) */
    protected final Class<?> getBeanClass(final Element element) {
        return ConversionField.class;
    }
    
    
    /** @see AbstractSingleBeanDefinitionParser#doParse(Element, ParserContext, 
     *       BeanDefinitionBuilder) */
    protected final void doParse(final Element element,
            final ParserContext parserContext,
            final BeanDefinitionBuilder bean) {

        bean.addConstructorArgValue(element.getAttribute("target"));
        bean.addConstructorArgReference(element.getAttribute("converter-ref"));
    
    }
}

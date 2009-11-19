/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.conversion.spring.schema;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;


import ar.com.zauber.commons.conversion.Converter;
import ar.com.zauber.commons.conversion.config.ConversionField;
import ar.com.zauber.commons.conversion.util.IdentityConverter;
import ar.com.zauber.commons.conversion.util.SinglePropertyConverter;

/**
 * Parses a complex property ConversionField definition
 * from the application context's XML.
 * 
 * @author Juan Edi
 * @since Nov 17, 2009
 */
public class SimplePropertyFieldDefinitionParser extends
        AbstractSimpleBeanDefinitionParser implements BeanDefinitionParser {

    /** @see AbstractSingleBeanDefinitionParser#getBeanClass(Element) */
    protected final Class<?> getBeanClass(final Element element) {
        return ConversionField.class;
    }
    
    
    /** @see AbstractSingleBeanDefinitionParser#doParse(Element, ParserContext, 
     *       BeanDefinitionBuilder) */
    @SuppressWarnings("unchecked")
    protected final void doParse(final Element element,
            final ParserContext parserContext,
            final BeanDefinitionBuilder bean) {

        String targetPropertyName =  element.getAttribute("target");
        String sourcePropertyName;
        Converter converter;

        bean.addConstructorArgValue(targetPropertyName);
        
        if (element.hasAttribute("source")) {
            sourcePropertyName = element.getAttribute("source");
        } else {
            sourcePropertyName =  targetPropertyName;
        }
        
                
        if (element.hasAttribute("converter-ref")) {
            BeanDefinition bd = BeanDefinitionBuilder
            .rootBeanDefinition(SinglePropertyConverter.class)
            .addConstructorArgValue(sourcePropertyName)
            .addConstructorArgReference(element.getAttribute("converter-ref"))
            .getBeanDefinition();
            bean.addConstructorArgValue(bd);
        } else {
            converter = new IdentityConverter();
            bean.addConstructorArgValue(
                    new SinglePropertyConverter(sourcePropertyName, converter));
        }
        
        
        
    }
    
}

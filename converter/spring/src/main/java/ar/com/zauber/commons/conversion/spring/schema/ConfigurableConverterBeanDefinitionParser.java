/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.conversion.spring.schema;

import java.util.List;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import ar.com.zauber.commons.conversion.ConfigurableJavaBeanConverter;

/**
 * Parses a ConfigurableJavaBeanConverterFactoryBean from 
 * the application context's XML.
 * 
 * 
 * @author Juan Edi
 * @since Nov 18, 2009
 */
public class ConfigurableConverterBeanDefinitionParser
                        extends AbstractBeanDefinitionParser {

    /** @see AbstractBeanDefinitionParser#parseInternal(Element, ParserContext) */
    @SuppressWarnings("unchecked")
    @Override
    protected final AbstractBeanDefinition parseInternal(final Element element,
                                            final ParserContext parserContext) {
        BeanDefinitionBuilder factory = BeanDefinitionBuilder.
            rootBeanDefinition(ConfigurableJavaBeanConverterFactoryBean.class);
        List fields = parserContext.getDelegate().parseListElement(
                        element, factory.getBeanDefinition());

        factory.addPropertyValue("converter",
                parseComponent(element).getBeanDefinition());
        factory.addPropertyValue("fields", fields);
        
        return factory.getBeanDefinition();
    }

    /**
     * 
     * Parses the ConfigurableJavaBeanConverter 
     * to be set as the FactoryBean's object. 
     * 
     * @param element
     * @return
     */
    private static BeanDefinitionBuilder parseComponent(final Element element) {
        BeanDefinitionBuilder component = BeanDefinitionBuilder.rootBeanDefinition(
                                                ConfigurableJavaBeanConverter.class);
        component.addConstructorArgValue(element.getAttribute("target-class"));
        return component;
    }
    
}

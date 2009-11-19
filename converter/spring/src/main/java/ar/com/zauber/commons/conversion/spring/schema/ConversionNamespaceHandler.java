/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.conversion.spring.schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * 
 * Handles the namespace's custom tags parsing.  
 * 
 * @author Juan Edi
 * @since Nov 17, 2009
 */
public class ConversionNamespaceHandler extends NamespaceHandlerSupport {

    
    /** @see org.springframework.beans.factory.xml.NamespaceHandler#init() */
    public final void init() {
        registerBeanDefinitionParser("configurable-converter",
                new ConfigurableConverterBeanDefinitionParser());
        
        registerBeanDefinitionParser("simple-property-field",
                new SimplePropertyFieldDefinitionParser());
        
        registerBeanDefinitionParser("complex-property-field",
                new ComplexPropertyFieldDefinitionParser());
    }

}

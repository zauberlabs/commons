/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

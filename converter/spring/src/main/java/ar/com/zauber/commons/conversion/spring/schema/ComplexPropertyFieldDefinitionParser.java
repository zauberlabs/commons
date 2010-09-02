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

import java.util.List;

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

        final List<?> l = parserContext.getDelegate().parseListElement(
                element, bean.getBeanDefinition());
        
        bean.addConstructorArgValue(element.getAttribute("target"));
        if(element.hasAttribute("converter-ref")) {
            bean.addConstructorArgReference(element.getAttribute("converter-ref"));
        } else if(l.size() == 1) {
            bean.addConstructorArgValue(l.iterator().next());    
        }
    
    }
}

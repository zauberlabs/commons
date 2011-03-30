/**
 * Copyright (c) 2005-2011 Zauber S.A. <http://www.zaubersoftware.com/>
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

import ar.com.zauber.commons.conversion.Converter;
import ar.com.zauber.commons.conversion.config.ConversionField;
import ar.com.zauber.commons.conversion.setters.FieldSetSetterStrategy;
import ar.com.zauber.commons.conversion.setters.FieldSetterStrategies;
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

        final String targetPropertyName =  element.getAttribute("target");
        String sourcePropertyName;
        Converter converter;

        bean.addConstructorArgValue(targetPropertyName);
        
        if (element.hasAttribute("source")) {
            sourcePropertyName = element.getAttribute("source");
        } else {
            sourcePropertyName =  targetPropertyName;
        }
        
        final List<?> l = parserContext.getDelegate().parseListElement(
                element, bean.getBeanDefinition());
        final BeanDefinitionBuilder bdb = BeanDefinitionBuilder
            .rootBeanDefinition(SinglePropertyConverter.class)
            .addConstructorArgValue(sourcePropertyName);
        
        if (element.hasAttribute("converter-ref")) {
            bean.addConstructorArgValue(
               bdb.addConstructorArgReference(element.getAttribute("converter-ref"))
               .getBeanDefinition());
        } else if (l.size() == 1) {
            bean.addConstructorArgValue(
                    bdb.addConstructorArgValue(l.iterator().next())
                    .getBeanDefinition());
        } else {
            converter = new IdentityConverter();
            bean.addConstructorArgValue(
                    new SinglePropertyConverter(sourcePropertyName, converter));
        }
        configureSetter(bean, element);
    }
    
    /** agrega el constructor del {@link FieldSetSetterStrategy} */
    static void configureSetter(final BeanDefinitionBuilder bean, 
            final Element element) {
        if(element.hasAttribute("setter")) {
            final String s = element.getAttribute("setter");
            if(s.equals("setter")) {
                bean.addConstructorArgValue(
                        FieldSetterStrategies.FIELD_SETTER_STRATEGY);
            } else if (s.equals("collection-add")) {
                bean.addConstructorArgValue(
                        FieldSetterStrategies.COLLECTION_ADD_STRATEGY);
            } else {
                throw new IllegalStateException("Unknown setter named " + s);
            }
        }    
    }
}

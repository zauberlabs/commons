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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandler;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.expression.Expression;
import org.w3c.dom.Element;

import ar.com.zauber.commons.conversion.Converter;
import ar.com.zauber.commons.conversion.util.CollectionToListConverter;
import ar.com.zauber.commons.conversion.util.CollectionToSetConverter;
import ar.com.zauber.commons.conversion.util.CollectionToSizeConverter;
import ar.com.zauber.commons.conversion.util.CompositeConverter;
import ar.com.zauber.commons.conversion.util.ExpressionExtractorConverter;
import ar.com.zauber.commons.conversion.util.FirstElementConverter;
import ar.com.zauber.commons.conversion.util.IdentityConverter;
import ar.com.zauber.commons.conversion.util.PropertyExtractorConverter;

/**
 * 
 * Handles the namespace's custom tags parsing.  
 * 
 * @author Juan Edi
 * @since Nov 17, 2009
 */
public class ConversionNamespaceHandler extends NamespaceHandlerSupport {
    
    /** @see NamespaceHandler#init() */
    public final void init() {
        registerBeanDefinitionParser("configurable-converter",
                new ConfigurableConverterBeanDefinitionParser());
        
        registerBeanDefinitionParser("simple-property-field",
                new SimplePropertyFieldDefinitionParser());
        
        registerBeanDefinitionParser("complex-property-field",
                new ComplexPropertyFieldDefinitionParser());
        
        registerBeanDefinitionParser("identity-converter", 
             new DefaultConstructorBeanDefinitionParser(IdentityConverter.class));
        registerBeanDefinitionParser("first-element-converter",
                new DefaultConstructorBeanDefinitionParser(
                        FirstElementConverter.class));
        registerBeanDefinitionParser("collection-to-size-converter",
                new DefaultConstructorBeanDefinitionParser(
                        CollectionToSizeConverter.class));
        registerBeanDefinitionParser("collection-to-list-converter", 
                new ConverterConstructorBeanDefinitionParser(
                        CollectionToListConverter.class));
        registerBeanDefinitionParser("collection-to-set-converter",
                new ConverterConstructorBeanDefinitionParser(
                        CollectionToSetConverter.class));
        registerBeanDefinitionParser("composite-converter",
                new AbstractSimpleBeanDefinitionParser() {
            @Override
            protected Class<?> getBeanClass(final Element element) {
                return CompositeConverter.class;
            }
            @Override
            protected void doParse(final Element element,
                    final ParserContext parserContext,
                    final BeanDefinitionBuilder builder) {
                final List<?> l = parserContext.getDelegate().parseListElement(
                        element, builder.getBeanDefinition());
                builder.addConstructorArgValue(l.get(0));
                builder.addConstructorArgValue(l.get(1));
            }
        });
        registerBeanDefinitionParser("property-extractor-converter", 
                new AbstractSimpleBeanDefinitionParser() {
            @Override
            protected Class<?> getBeanClass(final Element element) {
                return PropertyExtractorConverter.class;
            }
            @Override
            protected void doParse(final Element element,
                    final ParserContext parserContext,
                    final BeanDefinitionBuilder builder) {
                builder.addConstructorArgValue(element.getAttribute("property"));
            }
        });
        registerBeanDefinitionParser("expression-extractor-converter", 
                new AbstractSimpleBeanDefinitionParser() {
            @Override
            protected Class<?> getBeanClass(final Element element) {
                return ExpressionExtractorConverter.class;
            }
            @Override
            protected void doParse(final Element element,
                    final ParserContext parserContext,
                    final BeanDefinitionBuilder builder) {
                builder.addConstructorArgValue(element.getAttribute("expression"));
            }
        });
    }
}

/** default contructor {@link BeanDefinitionParser} */
class DefaultConstructorBeanDefinitionParser 
  extends AbstractSimpleBeanDefinitionParser {
    private final Class<?> clazz;

    /** Creates the DefaultConstructorBeanDefinitionParser. */
    public DefaultConstructorBeanDefinitionParser(final Class<?> clazz) {
        Validate.notNull(clazz);
        
        this.clazz = clazz;
    }
    
    @Override
    protected Class<?> getBeanClass(final Element element) {
        return clazz;
    }
} 

/** default contructor {@link BeanDefinitionParser} */
class ConverterConstructorBeanDefinitionParser 
  extends AbstractSimpleBeanDefinitionParser {
    private final Class<?> clazz;

    /** Creates the DefaultConstructorBeanDefinitionParser. */
    public ConverterConstructorBeanDefinitionParser(final Class<?> clazz) {
        Validate.notNull(clazz);
        
        this.clazz = clazz;
    }
    
    @Override
    protected Class<?> getBeanClass(final Element element) {
        return clazz;
    }
    
    @Override
    protected void doParse(final Element element, final ParserContext parserContext,
            final BeanDefinitionBuilder builder) {
        final String ref = element.getAttribute("element-converter-ref");
        if(StringUtils.isEmpty(ref)) {
            final List<?> l = parserContext.getDelegate().parseListElement(
                    element, builder.getBeanDefinition());
            if(l.size() != 1) {
                throw new IllegalStateException("Se esperaba un solo converter."
                        + " Se especificaron " + l.size());
            }
            builder.addConstructorArgValue(l.iterator().next());
        } else {
            builder.addConstructorArgReference(ref);
        }
    }
}
/**
 * Copyright (c) 2005-2015 Zauber S.A. <http://flowics.com/>
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

/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
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
package ar.com.zauber.commons.web.transformation.sanitizing.impl;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import ar.com.zauber.commons.web.transformation.sanitizing.api.TagSecutrityStrategy;

/**
 * Subclass of {@link AbstractElementNodeSanitizer} that removes
 * invalid Elements/Attributes.
 * 
 * @author Fernando Resco
 * @since Mar 6, 2009
 */
public class DeletingElementNodeSanitizer extends AbstractElementNodeSanitizer {

    /** Creates the DeletingElementNodeSanitizer. */
    public DeletingElementNodeSanitizer(
            final TagSecutrityStrategy tagSecutrityStrategy) {

        super(tagSecutrityStrategy);
    }
    
    /** @see AbstractElementNodeSanitizer#processInvalidElementAttributes(
     * Element, List) */
    @Override
    public final void processInvalidElementAttributes(final Element element,
            final List<Attr> invalidAttributes) {
        
        Validate.notNull(element);
        Validate.notNull(invalidAttributes);
        
        for(Attr attribute : invalidAttributes) {
            element.removeAttributeNode(attribute);
        }
    }

    /** @see AbstractElementNodeSanitizer#processInvalidElements(List) */
    @Override
    public final void processInvalidElements(final List<Element> invalidElements) {
        
        Validate.notNull(invalidElements);
        
        for(Element element : invalidElements) {
            if(element.getParentNode() != null) {
                element.getParentNode().removeChild(element);    
            }
        }
    }
}

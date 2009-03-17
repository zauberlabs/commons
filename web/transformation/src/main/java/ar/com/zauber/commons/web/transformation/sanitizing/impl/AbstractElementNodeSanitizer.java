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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ar.com.zauber.commons.web.transformation.sanitizing.api.TagSecutrityStrategy;
import ar.com.zauber.commons.web.transformation.sanitizing.api.XmlSanitizer;

/**
 * Implementation of XmlSanitizer that affects Element and Attribute Nodes.
 * 
 * What happens to invalid Element/Attributes must be defined by a subclass.
 * 
 * @author Fernando Resco
 * @since Mar 6, 2009
 */
public abstract class AbstractElementNodeSanitizer implements XmlSanitizer {

    private final TagSecutrityStrategy tagSecurityStrategy;
    
    /**
     * Creates the AbstractElementNodeSanitizer.
     *
     * @param tagSecutrityStrategy
     */
    public AbstractElementNodeSanitizer(
            final TagSecutrityStrategy tagSecutrityStrategy) {

        Validate.notNull(tagSecutrityStrategy);
        
        this.tagSecurityStrategy = tagSecutrityStrategy;
    }
    
    /** @see com.XmlSanitizer.golmix.utils.domSanitizing.DomSanitizer
     * #sanitizeNodeTree(org.w3c.dom.Node) */
    public final void sanitizeNodeTree(final Node node) {

        Validate.notNull(node);
        
        final List<Element> invalidElements = new ArrayList<Element>();
        
        sanitizeNode(node, invalidElements);

        processInvalidElements(invalidElements);
        
    }
    
    /**
     * Recursive tree traversal
     * @param node
     * @param invalidElements
     */
    private void sanitizeNode(final Node node, final List<Element> invalidElements) {
        if(node.getNodeType() == Node.ELEMENT_NODE) {
            final Element element = (Element)node;
            if(!tagSecurityStrategy.isTagAllowed(element.getNodeName())) {
                invalidElements.add(element);
                return;
            } else {
                final NamedNodeMap attributes = node.getAttributes(); 

                if(attributes.getLength() > 0) {

                    final List<Attr> invalidAttributes = new ArrayList<Attr>();

                    for(int i = 0; i < attributes.getLength(); ++i) {

                        final Attr attribute = (Attr)attributes.item(i); 

                        if(!tagSecurityStrategy.isAttributeAllowedForTag(
                                attribute.getNodeName(), element.getNodeName())
                                || !tagSecurityStrategy.isAttributeValueValidForTag(
                                        attribute.getNodeValue(),
                                        attribute.getNodeName(),
                                        element.getNodeName())) {

                            invalidAttributes.add(attribute);
                        }
                    }
                    processInvalidElementAttributes(element, invalidAttributes);
                }
            }
        }
        final NodeList children = node.getChildNodes();
        for(int i = 0; i < children.getLength(); ++i) {
            sanitizeNode(children.item(i), invalidElements);
        }
    }
    
    /**
     * Process invalid Elements.
     * @param invalidElements
     */
    public abstract void processInvalidElements(List<Element> invalidElements);
    
    /**
     * Process an element's invalid attributes.
     * @param element
     * @param invalidAttributes
     */
    public abstract void processInvalidElementAttributes(Element element,
            List<Attr> invalidAttributes);
}

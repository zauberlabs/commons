/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
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

    /**
     * Creates the DeletingElementNodeSanitizer.
     *
     * @param tagSecutrityStrategy
     */
    public DeletingElementNodeSanitizer(
            final TagSecutrityStrategy tagSecutrityStrategy) {

        super(tagSecutrityStrategy);
    }
    
    /** @see ar.com.zauber.commons.web.transformation.sanitizing.impl.clarin.golmix.utils.domSanitizing.AbstractElementNodeSanitizer
     * #processInvalidElementAttributes(org.w3c.dom.Element, java.util.List) */
    @Override
    public final void processInvalidElementAttributes(final Element element,
            final List<Attr> invalidAttributes) {
        
        Validate.notNull(element);
        Validate.notNull(invalidAttributes);
        
        for(Attr attribute : invalidAttributes) {
            element.removeAttributeNode(attribute);
        }
    }

    /** @see ar.com.zauber.commons.web.transformation.sanitizing.impl.clarin.golmix.utils.domSanitizing.AbstractElementNodeSanitizer
     * #processInvalidElements(java.util.List) */
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

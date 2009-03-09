/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.transformation.sanitizing.impl;

import ar.com.zauber.commons.web.transformation.sanitizing.api.AttributeValueValidator;

/**
 * Is attribute value a valid alignment?.
 * 
 * @author Fernando Resco
 * @since Mar 6, 2009
 */
public class StyleAlignmentOnlyValueValidator implements
        AttributeValueValidator {

    private static final String ALIGNMENT_LEFT = "text-align: left;";
    private static final String ALIGNMENT_CENTER = "text-align: center;";
    private static final String ALIGNMENT_RIGHT = "text-align: right;";
    private static final String ALIGNMENT_JUSTIFY = "text-align: justify;";
        
    /** @see com.clarin.golmix.utils.domSanitizing.AttributeValueValidator
     * #isAttributeValueValid(java.lang.String) */
    public final boolean isAttributeValueValid(final String attributeValue) {
        return ALIGNMENT_CENTER.equals(attributeValue)
            || ALIGNMENT_LEFT.equals(attributeValue)
                || ALIGNMENT_RIGHT.equals(attributeValue)
                    || ALIGNMENT_JUSTIFY.equals(attributeValue);
    }
}

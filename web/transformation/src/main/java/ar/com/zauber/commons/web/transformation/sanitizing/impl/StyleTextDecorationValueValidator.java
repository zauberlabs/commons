/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.transformation.sanitizing.impl;

import ar.com.zauber.commons.web.transformation.sanitizing.api.AttributeValueValidator;

/**
 * Is attribute value a valid decoration (line-through, underline) or a valid
 * color?.
 * 
 * @author Fernando Resco
 * @since Mar 6, 2009
 */
public class StyleTextDecorationValueValidator implements
        AttributeValueValidator {

    private static final String LINE_THROUGH = "text-decoration: line-through;";
    private static final String UNDERLINE = "text-decoration: underline;";
    private static final String COLOR_REGEXP = "color: #[abcdef0-9]{6};";
    
    /** @see com.clarin.golmix.utils.domSanitizing.AttributeValueValidator
     * #isAttributeValueValid(java.lang.String) */
    public final boolean isAttributeValueValid(final String attributeValue) {
        
        if(attributeValue == null) {
            return false;
        }
         return LINE_THROUGH.equals(attributeValue)
            || UNDERLINE.equals(attributeValue)
                || attributeValue.matches(COLOR_REGEXP);
    }
}

/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.transformation.sanitizing.impl;

import ar.com.zauber.commons.web.transformation.sanitizing.api.AttributeValueValidator;

/**
 * Is attribute value a valid target (_self, _blank)?.
 * 
 * @author Fernando Resco
 * @since Mar 6, 2009
 */
public class TargetSelfBlankValueValidator implements AttributeValueValidator {

    /** @see com.clarin.golmix.utils.domSanitizing.AttributeValueValidator
     * #isAttributeValueValid(java.lang.String) */
    public final boolean isAttributeValueValid(final String attributeValue) {
        return "_self".equals(attributeValue) || "_blank".equals(attributeValue);
    }
}

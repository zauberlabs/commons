/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.transformation.sanitizing.impl;

import java.net.MalformedURLException;
import java.net.URL;

import ar.com.zauber.commons.web.transformation.sanitizing.api.AttributeValueValidator;

/**
 * Is attribute value a correct URL?.
 * 
 * @author Fernando Resco
 * @since Mar 6, 2009
 */
public class HrefUrlOnlyValueValidator implements AttributeValueValidator {

    /** @see com.clarin.golmix.utils.domSanitizing.AttributeValueValidator
     * #isAttributeValueValid(java.lang.String) */
    public final boolean isAttributeValueValid(final String attributeValue) {
        try {
            new URL(attributeValue);
        } catch (MalformedURLException e) {
            return false;
        }
        return true;
    }
}

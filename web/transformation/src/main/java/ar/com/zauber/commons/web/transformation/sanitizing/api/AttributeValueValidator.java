/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.transformation.sanitizing.api;

/**
 * Attribute value validating interface.
 * 
 * @author Fernando Resco
 * @since Mar 6, 2009
 */
public interface AttributeValueValidator {
    
    /**
     * @param attributeValue
     * @return Si el attributeValue es válido.
     */
    boolean isAttributeValueValid(String attributeValue);
}

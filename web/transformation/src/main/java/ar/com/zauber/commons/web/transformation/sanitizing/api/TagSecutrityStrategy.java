/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.transformation.sanitizing.api;


/**
 * Interface for allowing/disallowing tag/attributes.
 * 
 * @author Fernando Resco
 * @since Mar 6, 2009
 */
public interface TagSecutrityStrategy {

    /**
     * @param tag
     * @return Is the tag allowed?.
     */
    boolean isTagAllowed(String tag);
    
    /**
     * @param attribute
     * @param tag
     * @return Is attribute allowed for tag?.
     */
    boolean isAttributeAllowedForTag(String attribute, String tag);
    
    /**
     * @param attributeValue
     * @param attribute
     * @param tag
     * @return Is value valid for tag's attribute?.
     */
    boolean isAttributeValueValidForTag(String attributeValue, String attribute,
            String tag);
}

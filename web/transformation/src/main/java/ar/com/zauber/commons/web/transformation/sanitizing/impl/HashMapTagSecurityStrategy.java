/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.transformation.sanitizing.impl;

import java.util.HashMap;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.transformation.sanitizing.api.AttributeValueValidator;
import ar.com.zauber.commons.web.transformation.sanitizing.api.TagSecutrityStrategy;

/**
 * HashMap based implementation of {@link TagSecutrityStrategy}.
 * 
 * @author Fernando Resco
 * @since Mar 6, 2009
 */
public class HashMapTagSecurityStrategy implements TagSecutrityStrategy {

    private final HashMap<String, HashMap<String,
        ? extends AttributeValueValidator>> allowedTags;
    
    /**
     * Creates the HashMapTagSecurityStrategy.
     *
     * @param allowedTags
     */
    public HashMapTagSecurityStrategy(final HashMap<String, HashMap<String,
            ? extends AttributeValueValidator>> allowedTags) {
    
        Validate.notNull(allowedTags);
        
        this.allowedTags = allowedTags;
    }
    
    /** @see com.clarin.golmix.utils.domSanitizing.TagSecutrityStrategy
     * #isTagAllowed(java.lang.String) */
    public final boolean isTagAllowed(final String tag) {
        return allowedTags.containsKey(tag);
    }
    
    /** @see com.clarin.golmix.utils.domSanitizing.TagSecutrityStrategy
     * #isAttributeAllowedForTag(java.lang.String, java.lang.String) */
    public final boolean isAttributeAllowedForTag(final String attribute,
            final String tag) {
        
        return allowedTags.containsKey(tag)
            ? allowedTags.get(tag).containsKey(attribute)
                    : false;
    }
    
    /** @see com.clarin.golmix.utils.domSanitizing.TagSecutrityStrategy
     * #isAttributeValueValidForTag(java.lang.String, java.lang.String,
     * java.lang.String) */
    public final boolean isAttributeValueValidForTag(final String attributeValue,
            final String attribute, final String tag) {
        
        final HashMap<String, ? extends AttributeValueValidator> allowedAttributes =
            allowedTags.get(tag); 
        if(allowedAttributes != null) {
            final AttributeValueValidator validator =
                allowedAttributes.get(attribute);
            if(validator != null) {
                return validator.isAttributeValueValid(attributeValue);
            }
        }
        return false;
    }
}

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

import java.util.Map;

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

    private final Map<String, Map<String, ? extends AttributeValueValidator>> 
                          allowedTags;
    
    /**
     * Creates the HashMapTagSecurityStrategy.
     *
     * @param allowedTags
     */
    public HashMapTagSecurityStrategy(final Map<String, Map<String,
            ? extends AttributeValueValidator>> allowedTags) {
    
        Validate.notNull(allowedTags);
        
        this.allowedTags = allowedTags;
    }
    
    /** @see TagSecutrityStrategy#isTagAllowed(java.lang.String) */
    public final boolean isTagAllowed(final String tag) {
        return allowedTags.containsKey(tag);
    }
    
    /** @see TagSecutrityStrategy#isAttributeAllowedForTag(String, String) */
    public final boolean isAttributeAllowedForTag(final String attribute,
            final String tag) {
        
        return allowedTags.containsKey(tag)
            ? allowedTags.get(tag).containsKey(attribute)
                    : false;
    }
    
    /** @see TagSecutrityStrategy#isAttributeValueValidForTag(String, String,
     *   String) */
    public final boolean isAttributeValueValidForTag(final String attributeValue,
            final String attribute, final String tag) {
        
        final Map<String, ? extends AttributeValueValidator> allowedAttributes =
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

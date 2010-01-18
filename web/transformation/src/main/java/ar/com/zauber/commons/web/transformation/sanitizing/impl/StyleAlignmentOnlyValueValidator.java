/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
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

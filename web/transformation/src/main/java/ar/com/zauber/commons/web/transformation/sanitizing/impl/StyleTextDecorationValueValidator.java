/**
 * Copyright (c) 2005-2011 Zauber S.A. <http://www.zaubersoftware.com/>
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

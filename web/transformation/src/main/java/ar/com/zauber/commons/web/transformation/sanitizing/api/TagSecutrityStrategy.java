/**
 * Copyright (c) 2005-2012 Zauber S.A. <http://www.zaubersoftware.com/>
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

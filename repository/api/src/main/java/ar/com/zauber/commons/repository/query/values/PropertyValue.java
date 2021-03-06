/**
 * Copyright (c) 2005-2015 Zauber S.A. <http://flowics.com/>
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
package ar.com.zauber.commons.repository.query.values;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

/**
 * Value to compare other properties.
 * 
 * @author Juan F. Codagnone
 * @since Mar 11, 2009
 */
public class PropertyValue implements Value {
    private final String otherProperty;


    /** Creates the PropertyValue. */
    public PropertyValue(final String otherProperty) {
        Validate.isTrue(!StringUtils.isBlank(otherProperty));
        
        this.otherProperty = otherProperty;
    }
    
    /** @return the name of the other property */
     public final String getOtherProperty() {
        return otherProperty;
    }
}

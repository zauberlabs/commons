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
package ar.com.zauber.commons.conversion.config;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.conversion.Converter;
import ar.com.zauber.commons.conversion.FieldSetterStrategy;
import ar.com.zauber.commons.conversion.setters.FieldSetterStrategies;


/**
 * Represents a field of the target object of the conversion to be populated, and
 * a converter that retrieves the value of the property from the source object of
 * the conversion. 
 * 
 * @param <S> Source type of the conversion.
 * @param <T> Target type of the conversion.
 *
 * @author Juan Edi
 * @since Nov 4, 2009
 */
public class ConversionField<S, T> {
    private final String targetName;
    private final Converter<S, T> converter;
    private final FieldSetterStrategy fieldStrategy;
    
    /** Creates the SimpleConversionField. */
    public ConversionField(final String targetName,
            final Converter<S, T> converter) {
        this(targetName, converter, FieldSetterStrategies.FIELD_SETTER_STRATEGY);
    }
    
    /** Creates the SimpleConversionField. */
    public ConversionField(final String targetName,
            final Converter<S, T> converter,
            final FieldSetterStrategy fieldStrategy) {
        Validate.notNull(targetName);
        Validate.notNull(converter);
        Validate.notNull(fieldStrategy);
        
        this.targetName = targetName;
        this.converter = converter;
        this.fieldStrategy = fieldStrategy;
    }

    /** Internal converter used to get the final property value. */
    public final Converter<S, T> getConverter() {
        return this.converter;
    }

    /** Target's field name to be populated in the conversion. */
    public final String getTargetFieldName() {
        return this.targetName;
    }

    @Override
    public final String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("[Field: ");
        buffer.append(targetName);
        buffer.append(" with: ");
        buffer.append(converter);
        buffer.append(" using strategy: ");
        buffer.append(fieldStrategy);
        buffer.append(" ]");
        return buffer.toString();
    }

    /** estrategia de seteos */
    public final FieldSetterStrategy getSetterStrategy() {
        return fieldStrategy;
    }
}

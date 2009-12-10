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
package ar.com.zauber.commons.conversion.config;

import java.util.HashSet;
import java.util.Set;


import ar.com.zauber.commons.conversion.Converter;
import ar.com.zauber.commons.conversion.util.IdentityConverter;
import ar.com.zauber.commons.conversion.util.SinglePropertyConverter;


/**
 * Conversion configuration. Holds all the ConversionFields the mapper will take
 * into account.
 * 
 * 
 * @param <S> Source type of the conversion. 
 * 
 * @author Juan Edi
 * @since Nov 3, 2009
 */
public class ConversionConfig<S> {

    private Set<ConversionField<? super S, ?>> fields = 
        new HashSet<ConversionField<? super S, ?>>();

    /**
     * 
     * Creates the ConversionConfig.
     * 
     */
    public ConversionConfig() {

    }

    /**
     * Creates the ConversionConfig.
     * 
     * @param fields
     *              ConversionFields to be taken into account in the conversion.
     */
    public ConversionConfig(final ConversionField<S, ?>[] fields) {
        for (int i = 0; i < fields.length; i++) {
            this.fields.add(fields[i]);
        }
    }

    /**
     * Creates and adds a conversionField to the configuration.
     * 
     * @param targetFieldName
     * @param converter
     * 
     */
    public final <T> void addField(final String targetFieldName,
            final Converter<S, T> converter) {
        fields.add(new ConversionField<S, T>(targetFieldName, converter));
    }

    /**
     * Adds a ConversionField to the configuration.
     * 
     * @param field ConversionField to be added to the configuration.
     */
    public final <T> void addField(final ConversionField<? super S, T> field) {
        this.fields.add(field);
    }

    
    /**
     * Adds a ConversionField that retrieves the property with the specified name
     * from the source object of the conversion and sets it as the property
     * with the same name of the target object.
     * Assumes that the property types match.
     * 
     * @param fieldName
     */
    public final void addSinglePropertyField(final String fieldName) {
        this.addSinglePropertyField(fieldName, fieldName,
                new IdentityConverter<S>());
    }

    /**
     * Adds a ConversionField that retrieves the property with the specified name
     * from the source object of the conversion and sets it as the property
     * with a different name of the target object.
     * Assumes that the property types match.
     * 
     * @param targetFieldName
     * @param sourceFieldName
     */
    public final void addSinglePropertyField(final String targetFieldName,
            final String sourceFieldName) {
        this.addSinglePropertyField(targetFieldName, sourceFieldName,
                new IdentityConverter<S>());
    }

    
    /**
     * 
     * Adds a ConversionField that converts a single property from the source object
     * to a property of the target object with a different type.  
     * 
     * Assumes that the property types match.
     * 
     * @param targetFieldName
     * @param sourceFieldName
     * @param internalConverter
     */
    public final <SF, T> void addSinglePropertyField(
            final String targetFieldName, final String sourceFieldName,
            final Converter<SF, T> internalConverter) {
        Converter<S, T> converter = new SinglePropertyConverter<S, SF, T>(
                sourceFieldName, internalConverter);

        this.addField(targetFieldName, converter);
    }

    /**
     * Returns the fields.
     * 
     * @return <code>Set<ConversionField<S,?>></code> with the fields.
     */
    public final Set<ConversionField<? super S, ?>> getFields() {
        return this.fields;
    }
}

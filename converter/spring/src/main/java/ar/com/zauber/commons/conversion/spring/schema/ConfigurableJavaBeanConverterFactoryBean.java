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
package ar.com.zauber.commons.conversion.spring.schema;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.FactoryBean;

import ar.com.zauber.commons.conversion.ConfigurableJavaBeanConverter;
import ar.com.zauber.commons.conversion.config.ConversionField;

/**
 * ConfigurableJavaBeanConverter FactoryBean. Adds all the ConversionFields
 * to the converter's configuration.
 * 
 * 
 * @author Juan Edi
 * @since Nov 18, 2009
 */
@SuppressWarnings("unchecked")
public class ConfigurableJavaBeanConverterFactoryBean
                implements FactoryBean<ConfigurableJavaBeanConverter> {

    private ConfigurableJavaBeanConverter converter;
    private List<ConversionField> fields;
    
    
    /** @see org.springframework.beans.factory.FactoryBean#getObject() */
    public final ConfigurableJavaBeanConverter getObject() throws Exception {
        if (this.fields != null && this.fields.size() > 0) {
            for (Iterator it = fields.iterator(); it.hasNext();) {
                converter.getConfig().addField((ConversionField) it.next());
            }
        }
        return converter;
    }

    /** @see org.springframework.beans.factory.FactoryBean#getObjectType() */
    public final Class<? extends ConfigurableJavaBeanConverter> getObjectType() {
        return ConfigurableJavaBeanConverter.class;
    }

    /** @see org.springframework.beans.factory.FactoryBean#isSingleton() */
    public final boolean isSingleton() {
        return true;
    }

    /**
     * Sets the converter. 
     *
     * @param converter
 *              <code>ConfigurableJavaBeanConverter</code> with the converter.
     */
    public final void setConverter(final ConfigurableJavaBeanConverter converter) {
        this.converter = converter;
    }

    /**
     * Sets the fields. 
     *
     * @param fields <code>List</code> with the fields.
     */
    public final void setFields(final List fields) {
        this.fields = fields;
    }



}

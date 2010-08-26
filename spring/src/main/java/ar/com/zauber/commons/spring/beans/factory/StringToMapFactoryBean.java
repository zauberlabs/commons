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
package ar.com.zauber.commons.spring.beans.factory;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.FactoryBean;

/**
 * {@link FactoryBean} that creates a {@link Map} with String keys and String
 * values, from a single String. Ex:
 * 
 * <pre>
 * &lt;bean class="ar.com.terra.stream.util.StringToMapFactoryBean"&gt;
 *    &lt;constructor-arg index="0" value="1=uno,2=dos,3=tres" /&gt;
 * &lt;/bean&gt;
 * </pre>
 * <p>Creates a Map with three entries: 1=uno, 2=dos and 3=tres.
 * <p>
 * Properties:
 * <ul>
 * <li><b>keyValueSeparatorRegex</b>: default is <code>"="</code>.
 * <li><b>elementSeparatorRegex</b>: default is <code>","</code>.
 * </ul>
 * 
 * @author Francisco J. González Costanzó
 * @since May 3, 2010
 */
public class StringToMapFactoryBean implements FactoryBean<Map<String, String>> {

    private final String property;
    private String keyValueSeparatorRegex = "=";
    private String elementSeparatorRegex = ",";

    private Map<String, String> instance;
    
    /**
     * Creates the StringToMapFactoryBean.
     * 
     * @param property
     */
    public StringToMapFactoryBean(final String property) {
        Validate.notNull(property);
        this.property = property;
    }    

    /**
     * Creates the StringToMapFactoryBean.
     * 
     * @param property
     * @param keyValueSeparatorRegex
     * @param elementSeparatorRegex
     */
    public StringToMapFactoryBean(final String property,
            final String keyValueSeparatorRegex,
            final String elementSeparatorRegex) {
        Validate.notNull(property);
        Validate.notNull(keyValueSeparatorRegex);
        Validate.notNull(elementSeparatorRegex);
        this.property = property;
        this.keyValueSeparatorRegex = keyValueSeparatorRegex;
        this.elementSeparatorRegex = elementSeparatorRegex;
    }

    /** @see org.springframework.beans.factory.FactoryBean#getObject() */
    public final Map<String, String> getObject() throws Exception {
        if (instance == null) {
            instance = getInstance();
        }

        return instance;
    }

    /**
     * @return la instancia
     */
    private Map<String, String> getInstance() {
        Map<String, String> out = new TreeMap<String, String>();
        String[] items = property.split(elementSeparatorRegex);
        for (String string : items) {
            if (StringUtils.isNotBlank(string)) {
                String[] element = string.split(keyValueSeparatorRegex);
                if (element != null && element.length == 2) {
                    if (StringUtils.isBlank(element[0])) {
                        throw new IllegalArgumentException("Blank key element:"
                                + string);
                    } else if (StringUtils.isBlank(element[1])) {
                        throw new IllegalArgumentException(
                                "Blank value element:" + string);
                    } else {
                        out.put(element[0].trim(), element[1].trim());
                    }
                } else {
                    throw new IllegalArgumentException("Error on element:"
                            + string);
                }
            }
        }
        return out;
    }

    /** @see org.springframework.beans.factory.FactoryBean#getObjectType() */
    public final Class<? extends Map<String, String>> getObjectType() {
        return null;
    }

    /** @see org.springframework.beans.factory.FactoryBean#isSingleton() */
    public final boolean isSingleton() {
        return true;
    }
    
    public final void setElementSeparatorRegex(
            final String elementSeparatorRegex) {
        this.elementSeparatorRegex = elementSeparatorRegex;
    }

    public final void setKeyValueSeparatorRegex(
            final String keyValueSeparatorRegex) {
        this.keyValueSeparatorRegex = keyValueSeparatorRegex;
    }

}

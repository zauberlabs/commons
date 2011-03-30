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
package ar.com.zauber.commons.web.proxy.impl.dao.properties.provider;

import java.util.Properties;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.proxy.impl.dao.properties.PropertiesProvider;

/**
 * Loads properties
 * 
 * @author Juan F. Codagnone
 * @since Aug 29, 2008
 */
public class SimplePropertiesProvider implements  PropertiesProvider {
    private final Properties properties;
   
    /** default constructor */
    public SimplePropertiesProvider() {
        properties = new Properties();
    }
    
    /** constructor */
    public SimplePropertiesProvider(final Properties properties) {
        Validate.notNull(properties);
        this.properties = properties;
    }
    
    /** @see PropertiesProvider#getProperties() */
    public final Properties getProperties() {
        return properties;
    }
}

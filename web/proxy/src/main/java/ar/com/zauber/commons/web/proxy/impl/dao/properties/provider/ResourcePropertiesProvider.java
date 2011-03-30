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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.Validate;
import org.springframework.core.io.Resource;

import ar.com.zauber.commons.web.proxy.impl.dao.properties.PropertiesProvider;

/**
 * PropertoesProvider que obtiene las properties a partir de un Resource.
 * 
 * @author Fernando Resco
 * @since Sep 19, 2008
 */
public class ResourcePropertiesProvider implements  PropertiesProvider {
    private final Properties properties;
    
    /** @throws IOException on error */
    public ResourcePropertiesProvider(final Resource resource) throws IOException {
        Validate.notNull(resource);
        
        properties = new Properties();
        final InputStream is = resource.getInputStream();
        
        try {
            properties.load(is);
        } finally {
            is.close();
        }
    }

    
    /** @see PropertiesProvider#getProperties() */
    public final Properties getProperties() {
        return properties;
    }

}

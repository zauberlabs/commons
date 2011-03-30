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
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.lang.Validate;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import ar.com.zauber.commons.web.proxy.impl.dao.properties.PropertiesProvider;

/**
 * PropertiesProvider que obtiene las propiedades a partir de un Resource definido 
 * por una variable jndi.
 * En caso de no encontrarlas recurre a una fallback.
 * 
 * @author Fernando Resco
 * @since Sep 19, 2008
 */
public class JndiPropertiesProvider implements PropertiesProvider {
    private PropertiesProvider target;
    
    
    /**
     * Creates the JndiPropertiesProvider.
     *
     * @param jndiVariable
     * @param resourceLoader
     * @param fallback
     * @throws IOException .
     */
    public JndiPropertiesProvider(
            final String jndiVariable, 
            final ResourceLoader resourceLoader,
            final PropertiesProvider fallback) throws IOException {
        Validate.notEmpty(jndiVariable);
        Validate.notNull(resourceLoader);
        Validate.notNull(fallback);
        
        try {
            final InitialContext initCtx = new InitialContext();
            final Context envCtx = (Context) initCtx.lookup("java:comp/env");
            final Resource resource = resourceLoader.getResource((String) envCtx
                                .lookup(jndiVariable));
            target = new ResourcePropertiesProvider(resource);
        } catch(final NamingException e) {
            target = fallback; 
        }
    }

    /** @see PropertiesProvider#getProperties() */
    public final Properties getProperties() {
        return target.getProperties();
    }

}

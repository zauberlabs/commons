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
package ar.com.zauber.commons.spring.configurers;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * Clase de utilidad
 * 
 */
public final class JndiInitialContextHelper {
    /** constructor oculto */
    private JndiInitialContextHelper() {
        // clase de utilidad
    }
    /** logger */
    public static final Log LOGGER = LogFactory
            .getLog(JndiInitialContextHelper.class);

    /** dado paths jndi retorna archivos de propiedades */
    public static Resource[] getJndiLocations(final String[] filePathJndiNames) {

        final ResourceLoader resourceLoader = new DefaultResourceLoader();

        try {
            final InitialContext initCtx = new InitialContext();
            final Context envCtx = (Context) initCtx.lookup("java:comp/env");
            final Resource[] locations = new Resource[filePathJndiNames.length];

            for (int i = 0; i < filePathJndiNames.length; i++) {
                locations[i] = resourceLoader.getResource((String) envCtx
                        .lookup(filePathJndiNames[i]));
            }
            return locations;
        } catch (final NamingException e) {
            LOGGER.warn("Hubo un error en el lookup de JNDI. Se usarán "
                      + "properties del classpath: " + e.getExplanation());
            return null;
        }
    }
}

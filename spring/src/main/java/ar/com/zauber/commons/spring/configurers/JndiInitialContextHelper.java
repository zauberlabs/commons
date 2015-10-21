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
package ar.com.zauber.commons.spring.configurers;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public static final Logger LOGGER = LoggerFactory
            .getLogger(JndiInitialContextHelper.class);

    /** dado paths jndi retorna archivos de propiedades */
    public static Resource[] getJndiLocations(final String[] filePathJndiNames) {

        final ResourceLoader resourceLoader = new DefaultResourceLoader();

        try {
            final InitialContext initCtx = new InitialContext();
            final Resource[] locations = new Resource[filePathJndiNames.length];
            boolean found = false;

            try {
                final Context envCtx = (Context) initCtx
                        .lookup("java:comp/env");
                for (int i = 0; i < filePathJndiNames.length; i++) {
                    locations[i] = resourceLoader.getResource((String) envCtx
                            .lookup(filePathJndiNames[i]));
                }
                found = true;
            } catch (final NamingException e) {
                LOGGER.warn("Error JNDI looking up 'java:comp/env':"
                        + e.getExplanation());
                // void
            }

            if (!found) {
                // Para Jetty 7 Server
                try {
                    for (int i = 0; i < filePathJndiNames.length; i++) {
                        locations[i] = resourceLoader
                                .getResource((String) initCtx
                                        .lookup(filePathJndiNames[i]));
                    }
                } catch (final NamingException e) {
                    LOGGER.warn("Hubo un error en el lookup de JNDI. Se usaran "
                            + "properties del classpath: " + e.getExplanation());
                    return null;
                }
            }

            return locations;
        } catch (final NamingException e) {
            LOGGER.warn("Hubo un error en el lookup de JNDI. Se usaran "
                    + "properties del classpath: " + e.getExplanation());
            return null;
        }
    }
}

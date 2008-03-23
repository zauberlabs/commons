/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
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
            LOGGER.error("Hubo un error en el lookup de JNDI: "
                    + e.getExplanation());
            return null;
        }
    }
}

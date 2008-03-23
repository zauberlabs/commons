/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.spring.configurers;

import org.springframework.beans.factory.config.PropertyOverrideConfigurer;
import org.springframework.core.io.Resource;

/**
 *
 * Clase para configurar por JNDI.
 *
 * Es una clase pensada para que el lookup se haga a partir de archivos con path
 * absoluto que se encuentran configurados via JNDI.
 *
 * @author Martin A. Marquez
 * @since Jul 18, 2007
 */
public class JndiPropertyOverrideConfigurer extends PropertyOverrideConfigurer {
    /** constructor */
    public JndiPropertyOverrideConfigurer() {
        super();
    }

    private String[] filePathJndiNames;

    /**
     * @param filePathJndiNames arreglo de paths jndi
     */
    public JndiPropertyOverrideConfigurer(final String[] filePathJndiNames) {
        super();
        this.filePathJndiNames = filePathJndiNames;
    }

    /** @see PropertiesLoaderSupport#setLocation(Resource) */
    @Override
    public final void setLocation(final Resource location) {
        this.setLocations(new Resource[] {location });
    }

    /** @see PropertiesLoaderSupport#setLocations(Resource) */
    @Override
    public final void setLocations(final Resource[] somelocations) {
        Resource[] locations = null;

        if (filePathJndiNames != null && filePathJndiNames.length > 0) {
            locations = JndiInitialContextHelper
                    .getJndiLocations(filePathJndiNames);
        }

        if (locations == null) {
            locations = somelocations;
        }

        super.setLocations(locations);
    }

}

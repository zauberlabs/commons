/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.proxy.impl.dao.properties.provider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.proxy.impl.dao.properties.PropertiesProvider;

/**
 * Lee las {@link Properties} desde un file con XML en el filesystem
 * 
 * @author Pablo Grigolatto
 * @since Jun 16, 2009
 */
public class FilesystemPropertiesProvider implements PropertiesProvider {
    private final File file;

    /** Creates the FilesystemPropertiesProvider. */
    public FilesystemPropertiesProvider(final File file) {
        Validate.notNull(file);
        this.file = file;
    }

    /** @see PropertiesProvider#getProperties() */
    public Properties getProperties() {
        try {
            final Properties properties = new Properties();
            final InputStream is = new FileInputStream(file);
            try {
                properties.loadFromXML(is);
                return properties;
            } finally {
                is.close();
            }
        } catch(final IOException e) {
            throw new UnhandledException(e);
        }
    }

}

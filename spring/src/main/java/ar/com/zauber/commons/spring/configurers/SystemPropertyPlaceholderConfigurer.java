/*
 * Copyright (c) 2008 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.spring.configurers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.AbstractResource;

/**
 * {@link PropertyPlaceholderConfigurer} que obtiene los valores a remplazar
 * de las propiedades del sistema.
 * 
 * Util para proveer propiedades desde un main.
 * 
 * @author Juan F. Codagnone
 * @since Mar 23, 2008
 */
public class SystemPropertyPlaceholderConfigurer extends
        PropertyPlaceholderConfigurer {

    /** constructor */
    public SystemPropertyPlaceholderConfigurer() {
        setLocation(new AbstractResource() {
            public String getDescription() {
                return "system properties";
            }

            public InputStream getInputStream() throws IOException {
                final ByteArrayOutputStream os = new ByteArrayOutputStream();
                System.getProperties().store(os, null);
                return new ByteArrayInputStream(os.toByteArray());
            }
            
            /** @see AbstractResource#getFilename() */
            @Override
            public String getFilename() throws IllegalStateException {
                return "system.properties";
            }
        });
    }
}

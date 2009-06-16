/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.proxy.impl.dao.properties.provider;

import java.io.File;
import java.net.URL;
import java.util.Properties;

import org.junit.Test;

import ar.com.zauber.commons.web.proxy.impl.dao.properties.PropertiesProvider;

import junit.framework.Assert;

/**
 * Pruebas para {@link FilesystemPropertiesProvider} 
 * 
 * @author Pablo Grigolatto
 * @since Jun 16, 2009
 */
public class FilesystemPropertiesProviderTest {
    private final PropertiesProvider fpp;

    /** Creates the FilesystemPropertiesProviderTest.*/
    public FilesystemPropertiesProviderTest() {
        final String classpath 
            = "ar/com/zauber/commons/web/proxy/impl/dao/properties/provider/";
        final URL url = getClass().getClassLoader().getSystemResource(
                classpath + "filesystemprovider.properties.xml");
        fpp = new FilesystemPropertiesProvider(new File(url.getFile()));
    }
    
    /** prueba cargar las propiedades */
    @Test
    public final void testFilesystemLoad() {
        Properties data = fpp.getProperties();
        Assert.assertEquals("^/hudson/(.*)$=http://localhost:8080/hudson/$1", 
                data.getProperty("3"));
        Assert.assertEquals("^/nexus/nexus/(.*)$=http://localhost:9095/nexus/$1", 
                data.getProperty("1"));
    }
    
}

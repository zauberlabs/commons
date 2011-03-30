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

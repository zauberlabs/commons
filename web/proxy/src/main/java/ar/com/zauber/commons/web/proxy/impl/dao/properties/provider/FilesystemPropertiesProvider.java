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

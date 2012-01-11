/**
 * Copyright (c) 2005-2012 Zauber S.A. <http://www.zaubersoftware.com/>
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
package ar.com.zauber.commons.web.proxy.impl.dao.properties.persister;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.proxy.impl.dao.properties.PropertiesPersister;

/**
 * Persists {@link Properties} as XML to a file in the filesystem
 * 
 * @author Juan F. Codagnone
 * @since Aug 29, 2008
 */
public class FilesystemPropertiesPersister implements PropertiesPersister {
    private final File file;

    /** constructor */
    public FilesystemPropertiesPersister(final File file) {
        Validate.notNull(file);
        this.file = file;
    }
    
    /** @see PropertiesPersister#save(Properties) */
    public final void save(final Properties properties) {
        try {
            final OutputStream os = new FileOutputStream(file);
            try {
                properties.storeToXML(os, new Date().toString());        
            } finally {
                os.close();
            }
        } catch(final IOException e) {
            throw new UnhandledException(e);
        }
        
    }
}

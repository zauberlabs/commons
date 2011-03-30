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
package ar.com.zauber.commons.web.version.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.web.version.VersionProvider;

/**
 * {@link VersionProvider} que usa una propiedad de un {@link Properties}.
 * 
 * @author Juan F. Codagnone
 * @since Jul 19, 2008
 */
public class PropertiesVersionProvider implements VersionProvider {
    private final String version;
    
    /**
     * Creates the PropertiesVersionProvider.
     *
     * @param properties
     * @param versionProperty
     */
    public PropertiesVersionProvider(final Properties properties, 
            final String versionProperty) {
        Validate.notNull(properties);
        Validate.isTrue(!StringUtils.isBlank(versionProperty));
        version = properties.getProperty(versionProperty, "");
    }
    
    /** Constructor provisto por conveniencia. Failsafe */
    public PropertiesVersionProvider(final String resouce, 
            final String versionProperty) {
        this(FailsafeInputStreamManifestProvider.getInputStream(resouce), 
                versionProperty);
    }
    
    /** Constructor provisto por conveniencia. Failsafe */
    public PropertiesVersionProvider(final InputStream is, 
            final String versionProperty) {
        final Properties properties = new Properties();
        
        if(is != null) {
            try {
                properties.load(is);
            } catch (final IOException e) {
                // ok
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    // ok
                }
            }
        }
        version = properties.getProperty(versionProperty, "");
    }
    /** @see VersionProvider#getVersion() */
    public final String getVersion() {
        return version;
    }
}

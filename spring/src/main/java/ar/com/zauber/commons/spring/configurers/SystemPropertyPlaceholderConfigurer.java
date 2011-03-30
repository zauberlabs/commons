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
package ar.com.zauber.commons.spring.configurers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

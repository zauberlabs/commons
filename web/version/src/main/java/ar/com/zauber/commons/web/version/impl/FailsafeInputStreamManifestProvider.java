/*
 * Copyright (c) 2005-2008 Zauber S.A. <http://www.zauber.com.ar/>
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Manifest;

import ar.com.zauber.commons.web.version.ManifestProvider;

/**
 * Provee {@link Manifest} desde un input stream. Si el input stream es invalido
 * igualmente provee un manifest nulo.
 * 
 * @author Juan F. Codagnone
 * @since Jul 19, 2008
 */
public class FailsafeInputStreamManifestProvider implements ManifestProvider {
    private final Manifest manifest;

    /** Creates the FailsafeClasspathManifestProvider. */
    public FailsafeInputStreamManifestProvider(final String filename) {
        this(getInputStream(filename));
    }
    
    /** Creates the FailsafeClasspathManifestProvider. */
    public FailsafeInputStreamManifestProvider(final InputStream is) {
        Manifest tmp = null;
        
        if(is != null) {
            try {
                try {
                    tmp = new Manifest(is);
                } catch(final IOException e) {
                    tmp = new Manifest();
                }
            } finally {
                try {
                    is.close();
                } catch (final IOException e) {
                    tmp = new Manifest();
                }
            }
        } else {
            tmp = new Manifest(); 
        }
        manifest = tmp;
    }
    
    /** @see ManifestProvider#getManifest() */
    public final Manifest getManifest() {
        return manifest;
    }
    

    /** loads the file */
    public static InputStream getInputStream(final String filename) {
        InputStream is = null;
        if(filename.startsWith("classpath:")) {
            is = FailsafeInputStreamManifestProvider.class.getClassLoader()
                .getResourceAsStream(filename.substring(10));
        } else {
            try {
                is = new FileInputStream(filename);
            } catch (FileNotFoundException e) {
                // ok;
            }
        }
        return is;
    }
}

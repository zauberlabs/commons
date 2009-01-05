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
package ar.com.zauber.common.image.impl;

import java.io.*;

import org.apache.commons.lang.Validate;

/**
 * Flyer stored in the filesystem. Relative path are saved (thanks to the 
 * reference to FileFlyerFactory) so it is portable around the filesystem.
 *
 * @author Juan F. Codagnone
 * @since Nov 14, 2005
 */
public class FileImage extends AbstractImage {
    /** the directory */
    private String directory;

    /**
     * Creates the FileFlyer.
     *
     * @param factory factory
     * @param directory eg: "a214s132d41asd
     * @param name my_nice_flyer.jpg"
     * @throws IOException on io error 
     * @throws IllegalArgumentException if the params are wrong 
     */
    public FileImage(final String directory, 
                     final String name)
                   throws IllegalArgumentException, IOException {
        super(name);
        Validate.notNull(directory, "directory");
        
        this.directory = directory;
    }
    
    /** @see ar.com.zauber.eventz.domain.event.Flyer#getInputStream() */
    public final InputStream getInputStream() throws IOException {
        return new FileInputStream(getFile());
    }

    /** @return the file where the flyer is stored */
    public final File getFile() {
        return new File(directory, getName());
    }
}

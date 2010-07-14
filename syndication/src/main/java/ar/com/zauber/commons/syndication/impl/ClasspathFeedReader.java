/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
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
package ar.com.zauber.commons.syndication.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.syndication.FeedReader;


/**
 * Implementación de {@link FeedReader} que busca el feed en 
 * classpath. Util para testeos. Se carga con el classloader.
 * 
 * @author Juan F. Codagnone
 * @since Jul 2, 2006
 * @deprecated no hay alternativa
 */
public class ClasspathFeedReader implements FeedReader {
    /** path a buscar el archivo */
    private final String path;
    
    /**
     * Creates the ClasspathFeedReader.
     *
     * @param path path of the resource
     */
    public ClasspathFeedReader(final String path) {
        Validate.notNull(path);
        
        this.path = path;
    }
    
    /** @see FeedReader#getReader() */
    public final Reader getReader() throws IOException {
        return new InputStreamReader(getClass().getClassLoader()
                .getResourceAsStream(path));
    }
}

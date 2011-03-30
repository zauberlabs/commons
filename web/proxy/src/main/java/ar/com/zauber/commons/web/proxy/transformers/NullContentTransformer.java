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
package ar.com.zauber.commons.web.proxy.transformers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.UnhandledException;

import ar.com.zauber.commons.web.proxy.ContentTransformer;

/**
 * 
 * Implementacion nula de transformador de salida. Copia lo que viene
 * del target hacia la salida.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Aug 7, 2007
 */
public class NullContentTransformer  implements ContentTransformer {

    /** @see OutputTransformer#getContentType() */
    public final String getContentType() {
        return null;
    }

    /** @see OutputTransformer#transform(InputStream, OutputStream) */
    public final void transform(final InputStream is, final OutputStream os,
                          final ContentMetadata metadata) {
        try {
            IOUtils.copy(is, os);
        } catch (final IOException e) {
            throw new UnhandledException(e);
        }
    }
}
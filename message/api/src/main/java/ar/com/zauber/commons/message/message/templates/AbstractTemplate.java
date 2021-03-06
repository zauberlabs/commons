/**
 * Copyright (c) 2005-2015 Zauber S.A. <http://flowics.com/>
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
package ar.com.zauber.commons.message.message.templates;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Resource;

/**
 * Abstract Template to build "messages" from a resource and a model.
 * It's used for both messagetemplate and messageparttemplate 
 * 
 * @author Christian Nardi
 * @since Dec 28, 2009
 */
public abstract class AbstractTemplate {

    /** modelo independiente de la vista / controlador. */
    private Map<String, Object> extraModel = new HashMap<String, Object>();

    /**
     * Copy bytes from a large (over 2GB) <code>InputStream</code> to an
     * <code>OutputStream</code>.
     * <p>
     * This method buffers the input internally, so there is no need to use a
     * <code>BufferedInputStream</code>.
     * 
     * @param input  the <code>InputStream</code> to read from
     * @param output  the <code>OutputStream</code> to write to
     * @return the number of bytes copied
     * @throws NullPointerException if the input or output is null
     * @throws IOException if an I/O error occurs
     * @since Commons IO 1.3
     */
    public static long copyLarge(final InputStream input, final OutputStream output)
            throws IOException {
                byte[] buffer = new byte[1024 * 4];
                long count = 0;
                int n = 0;
                while (-1 != (n = input.read(buffer))) {
                    output.write(buffer, 0, n);
                    count += n;
                }
                return count;
            }

    /** rendera un template y un modelo */
    protected abstract String renderString(
            final String string, final Map<String, Object> model);

    public final Map<String, Object> getExtraModel() {
        return extraModel;
    }

    /** @see #extraModel */
    public final void setExtraModel(final Map<String, Object> extraModel) {
        Validate.notNull(extraModel);
        Validate.noNullElements(extraModel.keySet());
        Validate.noNullElements(extraModel.values());
        
        this.extraModel = extraModel;
    }

    /**
     * Creates the AbstractTemplate.
     *
     */
    public AbstractTemplate() {
        super();
    }


    /**
     * @param content
     * @return
     */
    protected final ByteArrayOutputStream copyResource(final Resource content) {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        final InputStream is = content.getInputStream(); 
        try {
            copyLarge(is, os);
        } catch (final IOException e) {
            throw new UnhandledException(e);
        } finally {
            try {
                is.close();
            } catch (final IOException e) {
                throw new UnhandledException(e);
            }
        }
        return os;
    }
}
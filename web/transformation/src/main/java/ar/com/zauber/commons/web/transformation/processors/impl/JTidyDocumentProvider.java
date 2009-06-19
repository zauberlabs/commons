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
package ar.com.zauber.commons.web.transformation.processors.impl;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.lang.Validate;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;

import ar.com.zauber.commons.web.transformation.processors.DocumentProvider;

/**
 * {@link DocumentProvider} que usa JTidy
 * 
 * 
 * @author Juan F. Codagnone
 * @since May 30, 2009
 */
public class JTidyDocumentProvider implements DocumentProvider {
    private final Tidy tidy;
    
    /** Creates the JTidyDocumentProvider. */
    public JTidyDocumentProvider() {
        tidy = new Tidy(); // obtain a new Tidy instance
        tidy.setQuiet(true);
        tidy.setShowWarnings(false);
        tidy.setXHTML(false);
    }
    
    /** Creates the JTidyDocumentProvider. */
    public JTidyDocumentProvider(int encoding) {
        this();
        tidy.setCharEncoding(encoding);
    }
    
    /** Creates the JTidyDocumentProvider. */
    public JTidyDocumentProvider(final Tidy tidy) {
        Validate.notNull(tidy);
        this.tidy = tidy;
    }
    
    /** @see DocumentProvider#getInputStream(InputStream) */
    public final Document parse(final InputStream inputStream) {
        return tidy.parseDOM(inputStream, null);
    }

    /** @see DocumentProvider#serialize(Document, OutputStream) */
    public final void serialize(final Document document, 
            final OutputStream outputStream) {
        tidy.pprint(document, outputStream);
    }
}

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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import ar.com.zauber.commons.web.transformation.processors.DocumentProvider;

/**
 * Provee documentos DOMs
 * 
 * @author Juan F. Codagnone
 * @since May 30, 2009
 */
public class DocumentBuilderFactoryDocumentProvider implements DocumentProvider {
    // Using factory get an instance of document builder
    private final DocumentBuilderFactory dbf;
    
    /** Creates the DocumentBuilderFactoryDocumentProvider. */
    public DocumentBuilderFactoryDocumentProvider() {
        this(DocumentBuilderFactory.newInstance());
        dbf.setNamespaceAware(true);
    }
    
    /** Creates the DocumentBuilderFactoryDocumentProvider. */
    public DocumentBuilderFactoryDocumentProvider(
            final DocumentBuilderFactory dbf) {
        Validate.notNull(dbf);
        
        this.dbf = dbf;
    }

    /** @see DocumentProvider#getInputStream(InputStream) */
    public final Document parse(final InputStream inputStream) {
        
        
        try {
            final DocumentBuilder db = dbf.newDocumentBuilder();
            // parse using builder to get DOM representation of the XML file
            return db.parse(inputStream);
        } catch (ParserConfigurationException e) {
            throw new UnhandledException(e);
        } catch (SAXException e) {
            throw new UnhandledException(e);
        } catch (IOException e) {
            throw new UnhandledException(e);
        }
    }

    /** @see DocumentProvider#serialize(Document, OutputStream) */
    public final void serialize(final Document document, final OutputStream os) {
        try {
            TransformerFactory.newInstance().newTransformer().transform(
                    new DOMSource(document), new StreamResult(os));  
            os.flush();
        } catch(IOException e) {
            throw new UnhandledException(e);
        } catch (TransformerException e) {
            throw new UnhandledException(e);
        }
    }
}

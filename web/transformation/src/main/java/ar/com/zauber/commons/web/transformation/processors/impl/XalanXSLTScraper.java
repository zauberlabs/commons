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
package ar.com.zauber.commons.web.transformation.processors.impl;

import java.io.Writer;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;
import org.w3c.dom.Node;

import ar.com.zauber.commons.web.transformation.processors.DOMScraper;

/**
 * {@link DOMScraper} que utiliza un template XSLT para extraer y formar la
 * nueva representación de la entidad.
 *
 * @author Juan F. Codagnone
 * @since May 30, 2009
 */
public class XalanXSLTScraper implements DOMScraper {
    private final Source xsltSource;
    private final String encoding;

    /**
     * Creates the XSLTScraper.
     * @param encoding
     *
     */
    public XalanXSLTScraper(final Source xsltSource) {
        this(xsltSource, null);
    }


    /**
     * Creates the XalanXSLTScraper.
     * el encoding debe estar registrado.
     * @param xsltSource2
     * @param encoding
     */
    public XalanXSLTScraper(final Source xsltSource, final String encoding) {
        Validate.notNull(xsltSource);
        this.encoding = encoding;
        this.xsltSource = xsltSource;
    }


    /** @see DOMScraper#scrap(Node, Map, Writer) */
    public final void scrap(final Node node, final Map<String, Object> model,
            final Writer writer) {
        scrap(node, model, writer, null);
    }


    /**
     *
     * Propiedades que recibe un tranformer.
     * {@link javax.xml.transform.Transformer#setOutputProperties(Properties)}
     * utiliza estas opciones:
     *      javax.xml.transform.OutputKeys
     * @param node
     * @param model
     * @param writer
     * @param oformat siguiendo las specs linkeadas mas arriba
     *
     *
     * @throws TransformerFactoryConfigurationError
     */
    public void scrap(final Node node, final Map<String, Object> model,
            final Writer writer, Properties oformat)
            throws TransformerFactoryConfigurationError {
        Validate.notNull(node);
        Validate.notNull(model);
        Validate.notNull(writer);
        try {
            final TransformerFactory factory = TransformerFactory.newInstance();
            final Transformer transformer = factory.newTransformer(xsltSource);
            Validate.notNull(transformer);
            for(final Entry<String, Object> entry : model.entrySet()) {
                transformer.setParameter(entry.getKey(), entry.getValue());
            }
            Properties options;
            if(oformat != null) {
                options = new Properties(oformat);
            } else {
                options = new Properties();
            }
            if(encoding != null) {
                options.setProperty(OutputKeys.ENCODING, encoding);
            }
            transformer.setOutputProperties(options);
            transformer.transform(new DOMSource(node), new StreamResult(writer));
        } catch (TransformerException e) {
            throw new UnhandledException(e);
        }
    }
}

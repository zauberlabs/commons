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

import java.io.Writer;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
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

    /**
     * Creates the XSLTScraper.
     *
     */
    public XalanXSLTScraper(final Source xsltSource) {
        Validate.notNull(xsltSource);
        
        this.xsltSource = xsltSource;
    }
    
    
    /** @see DOMScraper#scrap(Node, Map, Writer) */
    public final void scrap(final Node node, final Map<String, Object> model, 
            final Writer writer) {
        try {
            final TransformerFactory factory = TransformerFactory.newInstance();
            final Transformer transformer = factory.newTransformer(xsltSource);
            Validate.notNull(transformer);
            for(final Entry<String, Object> entry : model.entrySet()) {
                transformer.setParameter(entry.getKey(), entry.getValue());
            }
            transformer.transform(new DOMSource(node), new StreamResult(writer));
        } catch (TransformerException e) {
            throw new UnhandledException(e);
        }
    }
}

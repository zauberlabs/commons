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
package ar.com.zauber.commons.web.transformation.processors.impl;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.tidy.Configuration;
import org.xml.sax.InputSource;

import ar.com.zauber.commons.web.transformation.processors.DocumentProvider;


/**
 * Clase que testea la clase xslt scrapper.
 *
 * @author Mariano Semelman
 * @since Dec 10, 2009
 */
public class XalanXSLTScraperTest {

    /** */
    @Test
    public final void testInit() throws Exception {
        String encoding = "utf-8";
        DocumentProvider prov = new DocumentBuilderFactoryDocumentProvider();
        Reader reader = new StringReader("<xml/>");
        Document n = prov.parse(new InputSource(reader));
        Source xsltSource = new DOMSource(n);
        XalanXSLTScraper scraper = new XalanXSLTScraper(xsltSource , encoding);
    }

}

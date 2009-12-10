/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
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

    /** */
    @Test
    public final void testScrap() throws Exception {
        String encoding = "utf-8";
        DocumentProvider prov = new DocumentBuilderFactoryDocumentProvider();
        Reader reader =
            new InputStreamReader(getClass().getResourceAsStream("test.xsl"),
                    Charset.forName("utf-8"));
        Document xsl = prov.parse(new InputSource(reader));
        Source xsltSource = new DOMSource(xsl);
        XalanXSLTScraper scraper = new XalanXSLTScraper(xsltSource , encoding);
        DocumentProvider prov2 = new JTidyDocumentProvider(Configuration.UTF8);
        Document doc = prov2.parse(getClass().getResourceAsStream("example.html"));
        Writer writer = new StringWriter();
        Map<String, Object> model = new TreeMap<String, Object>();
        scraper.scrap(doc, model, writer);
    }

}

/**
 *  Copyright (c) 2009-2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.transformation.processors.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import ar.com.zauber.commons.web.transformation.processors.DOMScraper;
import ar.com.zauber.commons.web.transformation.processors.DocumentProvider;

/**
 * Scrapper que aplica tidy y deja el contenido en un writer
 * 
 * Tidy tiene algunas complicaciones que no permite que se use un 
 * {@link InputSource}.
 * 
 * @author Cecilia Hagge
 * @since Jan 11, 2010
 */
public class TidyScrapper {
    private final DocumentProvider htmlProvider;
    private final DOMScraper scraper;
    
    /** Creates the CLNResultsClosure. */
    public TidyScrapper(
            final DocumentProvider htmlProvider,
            final DOMScraper scraper) {
        Validate.notNull(htmlProvider);
        Validate.notNull(scraper);


        this.htmlProvider = htmlProvider;
        this.scraper = scraper;
    }

    /** @see  */
    public final void scrap(final Reader content,  
            final Writer writer, final Map<String, Object> map) {
            Validate.notNull(writer);
            Validate.notNull(map);
            Validate.notNull(content);
            
            // se vuelca el contenido del response en formato utf8
            Writer dump = null;
            OutputStream htmlTemp = null;
            File html = null;
            try {
                html = File.createTempFile("tidyscrapper", "txt");
                html.deleteOnExit();
                htmlTemp = new FileOutputStream(html);
                dump = new OutputStreamWriter(htmlTemp, Charset.forName("utf-8"));
                IOUtils.copy(content, dump);
            } catch (FileNotFoundException e1) {
                throw new UnhandledException(e1);
            } catch (IOException e) {
                throw new UnhandledException(e);
            } finally {
                IOUtils.closeQuietly(dump);
                IOUtils.closeQuietly(htmlTemp);
            }
            
            // a partir del archivo se le pasa tidy a el content y se lo "scrapea"
            InputStream contenido = null;
            try {
                contenido = new FileInputStream(html);
                final Document doc = htmlProvider.parse(contenido);
                scraper.scrap(doc, map, writer);
            } catch (IOException e) {
                throw new UnhandledException(e);
            } finally {
                IOUtils.closeQuietly(writer);
                IOUtils.closeQuietly(contenido);
                if(html != null) {
                    html.delete();
                }
            }
    }
}

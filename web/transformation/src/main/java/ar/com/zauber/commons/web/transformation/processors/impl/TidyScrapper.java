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

import java.io.Reader;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.NullWriter;
import org.apache.commons.lang.Validate;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
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
    private final Tidy tidy;
    private final DocumentProvider documentProvider;
    private final DOMScraper scraper;
    
    /** Creates the TidyScrapper. */
    public TidyScrapper(
            final Tidy tidy,
            final DOMScraper scraper) {
        Validate.notNull(tidy);
        Validate.notNull(scraper);

        this.tidy = tidy;
        this.documentProvider = null;
        this.scraper = scraper;
    }

    /** Creates the TidyScrapper. */
    public TidyScrapper(
            final DocumentProvider documentProvider,
            final DOMScraper scraper) {
        Validate.notNull(documentProvider);
        Validate.notNull(scraper);

        this.tidy = null;
        this.documentProvider = documentProvider;
        this.scraper = scraper;
    }
    
    /** @see  */
    public final void scrap(final Reader content,  
            final Writer writer, final Map<String, Object> map) {
            Validate.notNull(writer);
            Validate.notNull(map);
            Validate.notNull(content);
            
            try {
                final Document doc;
                
                if(tidy != null) {
                    doc = tidy.parseDOM(content, new NullWriter());
                } else {
                    doc = documentProvider.parse(new InputSource(content));
                }
                scraper.scrap(doc, map, writer);
            } finally {
                IOUtils.closeQuietly(content);
            }
    }
}

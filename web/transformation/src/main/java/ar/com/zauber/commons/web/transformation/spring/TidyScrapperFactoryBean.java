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
package ar.com.zauber.commons.web.transformation.spring;

import java.io.InputStream;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.FactoryBean;
import org.w3c.tidy.Tidy;
import org.xml.sax.InputSource;

import ar.com.zauber.commons.web.transformation.processors.DocumentProvider;
import ar.com.zauber.commons.web.transformation.processors.impl.TidyScrapper;
import ar.com.zauber.commons.web.transformation.processors.impl.XalanXSLTScraper;

/**
 * Crea mas facilmente scrappers. Ej
 * <verbatim>
 * <bean name="categoryListScrapper" class="...TidyScrapperFactoryBean">
 *       <description>
 *           Scrapper para paginas como http://.../foo/bar
 *       </description>
 *       <constructor-arg index="0" value="com/foo/bar/trans/categorylist.xsl"/>
 *       <constructor-arg index="1" ref="htmlProvider"/>
 *       <constructor-arg index="2" ref="xmlProvider"/>
 *   </bean>
 * </verbatim>
 * @author Juan F. Codagnone
 * @since Jan 25, 2010
 */
public class TidyScrapperFactoryBean implements FactoryBean<TidyScrapper> {
    private final TidyScrapper tidyScrapper;
    
    /** Creates the TidyScrapperFactoryBean. */
    public TidyScrapperFactoryBean(final String path, 
            final Tidy jtidy, final DocumentProvider xml) {
        Validate.notNull(jtidy);
        Validate.notNull(xml);

        final InputStream is = getClass().getClassLoader().getResourceAsStream(path);
        try {
            final Source s1 =
                new DOMSource(xml.parse(new InputSource(is)));
            tidyScrapper = new TidyScrapper(jtidy, new XalanXSLTScraper(s1));
        } finally {
            IOUtils.closeQuietly(is);
        }
    }
    
    /** Creates the TidyScrapperFactoryBean. */
    public TidyScrapperFactoryBean(final String path, 
            final DocumentProvider documentProvider, final DocumentProvider xml) {
        Validate.notNull(documentProvider);
        Validate.notNull(xml);

        final InputStream is = getClass().getClassLoader().getResourceAsStream(path);
        try {
            final Source s1 =
                new DOMSource(xml.parse(new InputSource(is)));
            tidyScrapper = new TidyScrapper(documentProvider, new XalanXSLTScraper(s1));
        } finally {
            IOUtils.closeQuietly(is);
        }
    }    
    
    /** @see FactoryBean#getObject() */
    public final TidyScrapper getObject() throws Exception {
        return tidyScrapper;
    }

    /** @see FactoryBean#getObjectType() */
    public final Class<? extends TidyScrapper> getObjectType() {
        return tidyScrapper.getClass();
    }

    /** @see FactoryBean#isSingleton() */
    public final boolean isSingleton() {
        return true;
    }
}

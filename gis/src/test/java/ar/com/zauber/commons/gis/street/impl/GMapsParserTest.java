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
package ar.com.zauber.commons.gis.street.impl;

import java.io.InputStream;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

import ar.com.zauber.commons.gis.Result;


/**
 * Testeamos el parseo de xml de Google maps.
 *
 * @author Mariano Semelman
 * @since Dec 14, 2009
 */
public class GMapsParserTest {



    /** de una localidad que anda.    */
    @Test
    public final void testParse() throws Exception {
        final DocumentBuilderFactory factory = DocumentBuilderFactory
        .newInstance();
        factory.setNamespaceAware(true);
        final DocumentBuilder parser = factory.newDocumentBuilder();
        InputStream geolv8 = getClass().getResourceAsStream("/georesponse8.kml");
        Assert.assertNotNull(geolv8);
        final Document document = parser.parse(geolv8);
        GMapsResultGenerator gen = new GMapsResultGenerator();
        Collection<Result> results = gen.getResults(document);
        Assert.assertTrue(!results.isEmpty());
    }

    /** de una localidad que anda.    */
    @Test
    public final void testFalse() throws Exception {
        final DocumentBuilderFactory factory = DocumentBuilderFactory
        .newInstance();
        factory.setNamespaceAware(true);
        final DocumentBuilder parser = factory.newDocumentBuilder();
        InputStream geolv8 = getClass().getResourceAsStream("/georesponse4.kml");
        Assert.assertNotNull(geolv8);
        final Document document = parser.parse(geolv8);
        GMapsResultGenerator gen = new GMapsResultGenerator();
        Collection<Result> results = gen.getResults(document);
        Assert.assertTrue(results.isEmpty());
    }
}

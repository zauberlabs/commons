/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
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

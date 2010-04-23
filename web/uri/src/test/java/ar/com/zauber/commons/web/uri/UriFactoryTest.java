/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.commons.web.uri;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import ar.com.zauber.commons.web.uri.factory.RelativeUriFactory;

/**
 * {@link RelativeUriFactory} Test.
 * 
 * 
 * @author Mariano Cortesi
 * @since Jan 29, 2010
 */
public class UriFactoryTest {

    private RelativeUriFactory relativeUriFactory;

    /** set up */
    @Before
    public final void setUp() {
        Map<String, String> uris = new HashMap<String, String>();
        uris.put("propertiesAndMethods",
                "/{#root[0].propA}+{#root[0].computedProperty()}+"
                        + "{#root[0].propertyCollection()[2]}");
        uris.put("singleArgument", "/hola/que/tal/{#root[0].propA}");
        uris.put("multipleArguments",
                "/hola/que/tal/{#root[0].propA}...{#root[1]}");
        this.relativeUriFactory = new RelativeUriFactory(
                new SpelExpressionParser(), uris);
    }

    /** test */
    @Test
    public final void propertiesAndMethods() {
        String uri = this.relativeUriFactory.buildUri("propertiesAndMethods",
                new Stub());
        assertEquals("/hola+5+tres", uri);
    }

    /** test */
    @Test
    public final void singleArgument() {
        String uri = this.relativeUriFactory.buildUri("singleArgument", new Stub());
        assertEquals("/hola/que/tal/hola", uri);
    }

    /** test */
    @Test
    public final void multipleArguments() {
        String uri = this.relativeUriFactory.buildUri("multipleArguments", new Stub(),
                new Integer(90));
        assertEquals("/hola/que/tal/hola...90", uri);

    }
}

/** TODO javadoc */
class Stub {
    private String propA = "hola";

    public String getPropA() {
        return this.propA;
    }

    /** TODO javadoc */
    public Integer computedProperty() {
        return 5;
    }

    /** TODO javadoc */
    public Collection<String> propertyCollection() {
        return Arrays.asList(new String[] {"uno", "dos", "tres"});
    }
}
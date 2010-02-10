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

/**
 * TODO Descripcion de la clase. Los comentarios van en castellano.
 * 
 * 
 * @author Mariano Cortesi
 * @since Jan 29, 2010
 */
public class UriFactoryTest {

    private UriFactory uriFactory;

    /** set up */
    @Before
    public final void setUp() {
        Map<String, String> uris = new HashMap<String, String>();
        uris.put("propertiesAndMethods",
                "{#root[0].propA}+{#root[0].computedProperty()}+"
                        + "{#root[0].propertyCollection()[2]}");
        uris.put("singleArgument", "hola/que/tal/{#root[0].propA}");
        uris.put("multipleArguments",
                "hola/que/tal/{#root[0].propA}...{#root[1]}");
        this.uriFactory = new UriFactory("http://localhost/", "BASE/",
                new SpelExpressionParser(), uris);
    }

    /** test */
    @Test
    public final void propertiesAndMethods() {
        String uri = this.uriFactory.buildUri("propertiesAndMethods",
                new Stub());
        String noHostUri = this.uriFactory.buildUri(false,
                "propertiesAndMethods", new Stub());
        assertEquals("http://localhost/BASE/hola+5+tres", uri);
        assertEquals("BASE/hola+5+tres", noHostUri);
    }

    /** test */
    @Test
    public final void singleArgument() {
        String uri = this.uriFactory.buildUri("singleArgument", new Stub());
        String noHostUri = this.uriFactory.buildUri(false, "singleArgument",
                new Stub());
        assertEquals("http://localhost/BASE/hola/que/tal/hola", uri);
        assertEquals("BASE/hola/que/tal/hola", noHostUri);
    }

    /** test */
    @Test
    public final void multipleArguments() {
        String uri = this.uriFactory.buildUri("multipleArguments", new Stub(),
                new Integer(90));
        String noHostUri = this.uriFactory.buildUri(false, "multipleArguments",
                new Stub(), new Integer(90));
        assertEquals("http://localhost/BASE/hola/que/tal/hola...90", uri);
        assertEquals("BASE/hola/que/tal/hola...90", noHostUri);

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
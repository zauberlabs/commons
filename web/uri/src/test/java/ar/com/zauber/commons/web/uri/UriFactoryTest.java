/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
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

    @Before
    public void setUp() {
        Map<String,String> uris = new HashMap<String, String>();
        uris.put("propertiesAndMethods", "{#root[0].propA}+{#root[0].computedProperty()}+{#root[0].propertyCollection()[2]}");
        uris.put("singleArgument", "hola/que/tal/{#root[0].propA}");
        uris.put("multipleArguments", "hola/que/tal/{#root[0].propA}...{#root[1]}");
        this.uriFactory = new UriFactory("BASE/", new SpelExpressionParser(), uris);
    }
    
    @Test
    public void propertiesAndMethods() {
        String uri = this.uriFactory.buildUri("propertiesAndMethods", new Stub());
        assertEquals("BASE/hola+5+tres", uri);
    }
    
    @Test
    public void singleArgument() {
        String uri = this.uriFactory.buildUri("singleArgument", new Stub());
        assertEquals("BASE/hola/que/tal/hola", uri);
    }
    
    @Test
    public void multipleArguments() {
        String uri = this.uriFactory.buildUri("multipleArguments", new Stub(), new Integer(90));
        assertEquals("BASE/hola/que/tal/hola...90", uri);
        
    }
}


class Stub {
    private String propA = "hola";

    public String getPropA() {
        return this.propA;
    }
    
    public Integer computedProperty() {
        return 5;
    }
    
    public Collection<String> propertyCollection() {
        return Arrays.asList(new String[] {"uno","dos","tres"});
    }
}
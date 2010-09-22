/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.factory;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;


/**
 * Test for {@link AnnotationExpressionMapUriFactoryFactoryBean}
 * 
 * 
 * @author Juan F. Codagnone
 * @since Sep 5, 2010
 */
@ContextConfiguration
public class ExpressionMapUriFactoryFactoryBeanTest 
     extends AbstractJUnit4SpringContextTests {
    @Resource private ExpressionMapUriFactory uriFactory;
    
    /** prueba el caso que se pide una key del mapa statico de uris */
    @Test
    public final void testExtraParam() {
        Assert.assertEquals("/", uriFactory.buildUri("root"));
    }
    
    /** prueba el caso de que se pide una llave incorrecta */
    @Test(expected = IllegalArgumentException.class)
    public final void testUnknownKey() {
        uriFactory.buildUri("lalala");
    }
    
    /** se pide una uri con anotacion */
    @Test
    public final void testDinamico() {
        Assert.assertEquals("/v1/u/juan",  uriFactory.buildUri("usuario", 
                "juan"));
    }

}

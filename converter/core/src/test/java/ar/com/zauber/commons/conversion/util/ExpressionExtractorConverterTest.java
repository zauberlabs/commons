/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.conversion.util;

import junit.framework.Assert;

import org.junit.Test;

import ar.com.zauber.commons.conversion.Converter;
import ar.com.zauber.commons.conversion.Foo;


/**
 * TODO Descripcion de la clase. Los comenterios van en castellano.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Sep 2, 2010
 */
public class ExpressionExtractorConverterTest {

    /** test */
    @Test
    public void foo() {
        final Converter c = new ExpressionExtractorConverter("bar.string");
        final String msg = "hola";
        Assert.assertEquals(msg, c.convert(new Foo(msg), null));
    }
}

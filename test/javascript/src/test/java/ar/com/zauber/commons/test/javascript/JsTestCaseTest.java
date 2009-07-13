/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.test.javascript;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Pruebas para {@link JsTestCase}
 * 
 * @author Pablo Grigolatto
 * @since Jul 13, 2009
 */
public class JsTestCaseTest {
    
    /** full ok test run */
    @Test
    public final void greenTest() throws Throwable {
        final JsTestCase jtc = new JsTestCase() {
            @Override
            protected String[] getIncludes() {
                return new String[] {
                    "src/test/resources/BlaTest.js" 
                };
            }
        };
        jtc.setName("testDriver");
        jtc.setUp();
        jtc.runTest();
    }
    
    /** default show value == false */
    @Test
    public final void defualtShowValue() {
        final JsTestCase jtc = new JsTestCase() {
            @Override
            protected String[] getIncludes() {
                return null;
            }
        };
        Assert.assertEquals(false, jtc.showDetails());
    }
}

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
        Assert.assertEquals(true, jtc.showDetails());
    }
}

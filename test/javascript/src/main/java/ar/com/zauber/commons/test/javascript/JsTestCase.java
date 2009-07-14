/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
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


import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;

import junit.framework.Assert;
import junit.framework.TestCase;
import de.berlios.jsunit.JsUnitException;
import de.berlios.jsunit.JsUnitRhinoRunner;

/**
 * Clase base para realizar testing de código javascipt
 * 
 * @author Pablo Grigolatto
 * @since Jul 10, 2009
 */
public abstract class JsTestCase extends TestCase {
    private static final String JAVASCRIPT_TEST_SHOWDETAILS 
        = "javascript.test.showdetails";
    private JsUnitRhinoRunner runner;

    /** @see TestCase#setUp() */
    @Override
    protected final void setUp() throws Exception {
        super.setUp();
        this.runner = new JsUnitRhinoRunner();
    }

    /** @see TestCase#runTest() */
    @Override
    protected final void runTest() throws Throwable {
        super.runTest();

        final StringWriter writer = new StringWriter();
        loadScripts();
        
        runner.runTestCases(writer, null);

        final String xml = writer.toString();
        Assert.assertTrue("Javascript test: \n" + xml, 
                xml.contains("errors=\"0\" failures=\"0\""));
        
        if(showDetails() 
           || Boolean.parseBoolean(
                   System.getProperty(JAVASCRIPT_TEST_SHOWDETAILS, "false"))) {
            System.out.println(xml);
        }
        writer.close();
    }

    /** includes */
    private void loadScripts() throws JsUnitException, IOException {
        for (String filename : getIncludes()) {
            final FileReader reader = new FileReader(filename);
            runner.load(reader, filename);
        }
    }
    
    /** default test */
    public void testDriver() {
        //ok, nothing to do
    }

    /**
     * @return
     *  Un Array con las rutas a los archivos a incluir en el contexto del test.
     *  Por ejemplo:
     *  <pre>
     *  return new String[] {
     *      "src/main/javascript/validate.js",
     *      "src/test/javascript/ValidateTest.js"
     *   };
     *   </pre>
     */
    protected abstract String[] getIncludes();
   
    /** 
     * @return <code>true</code> si se desea imprimir la informacion por consola.
     * <code>true</code> por default. 
     * */
    //CHECKSTYLE:DESIGN:OFF
    protected boolean showDetails() {
        return true;
    }
    //CHECKSTYLE:DESIGN:ON
  
}

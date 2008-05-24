/*
 * Copyright (c) 2005-2008 Zauber S.A. <http://www.zauber.com.ar/>
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
package ar.com.zauber.commons.spring.web;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;


/**
 * Test for <code>CommandURLParameterGenerator</url>
 * 
 * @see ar.com.zauber.commons.web.spring.CommandURLParameterGenerator
 * @author Juan F. Codagnone
 * @since Jun 24, 2005
 */
public class CommandURLParameterGeneratorTest extends TestCase {
    /**
     * 
     * @throws Exception on erroro
     */
    public final void testSomething() throws Exception {
        final Set<Object> set = new HashSet<Object>();
        set.add(new URL("http://www.zauber.com.ar/"));
        set.add("¿hola mundo?");
        set.add(TestEnum.BAR);
        
        final String ret =  CommandURLParameterGenerator.getURLParameter(
                TestCommand.class, set);
        System.out.println(ret);
    }
    
    /** an enum to test things */
    enum TestEnum {
        /** foo */
        FOO,
        /** bar  */
        BAR
    }
    
    /** an command to test things */
    final class TestCommand {
        /** a name */
        private String name;
        /** an url  */
        private URL url;
        /** an enum */
        private TestEnum foobar;
        
        /**
         * Returns the foobar.
         * 
         * @return <code>TestEnum</code> with the foobar.
         */
        public TestEnum getFoobar() {
            return foobar;
        }
        
        /**
         * Sets the foobar. 
         *
         * @param foobar <code>TestEnum</code> with the foobar.
         */
        public void setFoobar(final TestEnum foobar) {
            this.foobar = foobar;
        }
        
        /**
         * Returns the name.
         * 
         * @return <code>String</code> with the name.
         */
        public String getName() {
            return name;
        }
        
        /**
         * Sets the name. 
         *
         * @param name <code>String</code> with the name.
         */
        public void setName(final String name) {
            this.name = name;
        }
        
        /**
         * Returns the url.
         * 
         * @return <code>URL</code> with the url.
         */
        public URL getUrl() {
            return url;
        }
        
        /**
         * Sets the url. 
         *
         * @param url <code>URL</code> with the url.
         */
        public void setUrl(final URL url) {
            this.url = url;
        }
        
        /**
         * Sets the NotMatch
         * @param n a number
         */
        public void setNotMatch(final int n) {
        }
        
        /**
         * @return a string
         */
        public String getNotMatch() {
            return "";       
        }
        
    }
}

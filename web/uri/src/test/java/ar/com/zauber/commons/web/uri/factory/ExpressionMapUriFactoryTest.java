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
package ar.com.zauber.commons.web.uri.factory;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * {@link ExpressionMapUriFactory} Test.
 * 
 * @author Mariano Cortesi
 * @since Jan 29, 2010
 */
public class ExpressionMapUriFactoryTest {

    private ExpressionMapUriFactory expMapUriFactory;
    private ExpressionMapUriFactory uriTemplateMapUriFactory;

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
        uris.put("encodedArgument",
                "/hola/que/tal/{#encode(#root[0].propB)}");
        expMapUriFactory = new ExpressionMapUriFactory(
                new SpelExpressionParser(), uris);
        
        final Map<String, String> turis = new HashMap<String, String>();
        turis.put("usuario", "/v1/u/{username}/empresas/");
        turis.put("foo", "bar");
        uriTemplateMapUriFactory = new ExpressionMapUriFactory(turis, 
                ExpressionMapUriFactory.Type.URITEMPLATE);
    }

    /** test */
    @Test
    public final void propertiesAndMethods() {
        String uri = this.expMapUriFactory.buildUri("propertiesAndMethods",
                new Stub());
        assertEquals("/hola+5+tres", uri);
    }

    /** test */
    @Test
    public final void singleArgument() {
        String uri = this.expMapUriFactory.buildUri("singleArgument", new Stub());
        assertEquals("/hola/que/tal/hola", uri);
    }

    /** test */
    @Test
    public final void multipleArguments() {
        String uri = this.expMapUriFactory.buildUri("multipleArguments", new Stub(),
                new Integer(90));
        assertEquals("/hola/que/tal/hola...90", uri);

    }
    
    /** Test de ..*/
    @Test
    public final void encodeArgument() throws Exception {
        String uri = expMapUriFactory.buildUri("encodedArgument", new Stub());
        assertEquals("/hola/que/tal/hola%20como%20va%3F%3A%20bien%2Bvos%3F", uri);
    }

    /** Test de ..*/
    @Test
    public final void uriTemplateWithParam() {
        assertEquals("/v1/u/juan/empresas/", 
                uriTemplateMapUriFactory.buildUri("usuario", "juan"));
    }
    
    /** Test de ..*/
    @Test(expected = IllegalArgumentException.class)
    public final void uriTemplateWithParamMissing() {
        assertEquals("/v1/u/juan/empresas/", 
                uriTemplateMapUriFactory.buildUri("usuario"));
    }
    
    /** Test de ..*/
    @Test
    public final void uriTemplateNoParam() {
        assertEquals("bar", uriTemplateMapUriFactory.buildUri("foo"));
    }
    
    /** Test Stub */
    private static class Stub {
        private String propA = "hola";
        private String propB = "hola como va?: bien+vos?";
        
        @SuppressWarnings("unused")
        public String getPropA() {
            return this.propA;
        }
        
        @SuppressWarnings("unused")
        public String getPropB() {
            return propB;
        }
        
        /** TODO javadoc */
        @SuppressWarnings("unused")
        public Integer computedProperty() {
            return 5;
        }
        
        /** TODO javadoc */
        @SuppressWarnings("unused")
        public Collection<String> propertyCollection() {
            return Arrays.asList(new String[] {"uno", "dos", "tres"});
        }
    }
}

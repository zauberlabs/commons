/**
 * Copyright (c) 2005-2015 Zauber S.A. <http://flowics.com/>
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

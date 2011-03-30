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


import org.junit.Assert;
import org.junit.Test;


/**
 * Test de {@link AbsolutePathUriFactory}
 * 
 * @author Mariano Semelman
 * @since Jun 18, 2010
 */
public class AbsolutePathUriFactoryTest {

    /** test de construcción. */
    @Test
    public final void setUp() throws Exception {
        new AbsolutePathUriFactory(new PrefixUriFactory("foo:", 
                new IdentityUriFactory()));
    }
    
    /** */
    @Test
    public final void testAbsolute() throws Exception {
        AbsolutePathUriFactory uf = 
            new AbsolutePathUriFactory(new PrefixUriFactory("foo:", 
                new IdentityUriFactory()));
        Assert.assertEquals("foo:bar", uf.buildUri("bar"));
        Assert.assertEquals("http://bar", uf.buildUri("http://bar"));
    }
}

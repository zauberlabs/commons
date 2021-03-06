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

import static junit.framework.Assert.assertEquals;

import org.junit.Test;
import org.springframework.mock.web.MockServletContext;


/**
 * {@link ServletPathUriFactory} Test case
 * 
 * @author Mariano Cortesi
 * @since May 10, 2010
 */
public class ServletPathUriFactoryTest {

    /** test case */
    @Test
    public final void testServletPath() {
        MockServletContext servletContext = new MockServletContext();
        servletContext.setContextPath("my-path/");
        ServletPathUriFactory uriFactory = 
            new ServletPathUriFactory(new IdentityUriFactory());
        uriFactory.setServletContext(servletContext);
        
        assertEquals("my-path/hello", uriFactory.buildUri("hello"));
    }
}

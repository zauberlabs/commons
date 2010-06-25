/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
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
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockServletContext;

/**
 * {@link ConfigurableUriFactoryFactoryBean} Test Case
 * 
 * @author Mariano Cortesi
 * @since May 10, 2010
 */
public class ConfigurableUriFactoryFactoryBeanTest {

    private ConfigurableUriFactoryFactoryBean factoryBean;
    
    /** creates the factory bean */
    @Before
    public final void testCreateFactoryBean() {
        MockServletContext servletContext = new MockServletContext();
        servletContext.setContextPath("/my-path");
        this.factoryBean = new ConfigurableUriFactoryFactoryBean(
                new IdentityUriFactory());
        this.factoryBean.setServletContext(servletContext);        
    }
    
    /** test method */
    @Test(expected = IllegalArgumentException.class)
    public final void testWithInvalidPrefix() throws Exception {
        this.factoryBean.setPrefixKey("basura");
        this.factoryBean.getObject();
    }
    
    /** test method */
    @Test
    public final void testWithPrefix() throws Exception {
        this.factoryBean.setPrefixKey("static:http://www.google.com");
        UriFactory uriFactory = this.factoryBean.getObject();
        assertEquals("http://www.google.com/hola", uriFactory.buildUri("/hola"));
    }

    /** test method */
    @Test
    public final void testWithServletPath() throws Exception {
        this.factoryBean.setPrefixKey("servlet-path");
        UriFactory uriFactory = this.factoryBean.getObject();
        assertEquals("/my-path/hola", uriFactory.buildUri("/hola"));
    }

    /** test method */
    @Test
    public final void testWithNoPrefix() throws Exception {
        UriFactory uriFactory = this.factoryBean.getObject();
        assertEquals("/hola", uriFactory.buildUri("/hola"));
    }
    
    /** test method */
    @Test
    public final void testWithVersion() throws Exception {
        this.factoryBean.setVersion("0.3");
        UriFactory uriFactory = this.factoryBean.getObject();
        assertEquals("/hola?v=0.3", uriFactory.buildUri("/hola"));
    }
    
    /** test method */
    @Test
    public final void testWithAbsolute() throws Exception {
        this.factoryBean.setPrefixKey("static:crap-");
        UriFactory f = this.factoryBean.getObject();
        Assert.assertEquals("http://bar", f.buildUri("http://bar"));
        Assert.assertEquals("crap-bar", f.buildUri("bar"));
    }
    
    /** test method */
    @Test
    public final void testWithoutAbsolute() throws Exception {
        this.factoryBean.setPrefixKey("static:crap-");
        this.factoryBean.setAbsoluteUris(false);
        UriFactory f = this.factoryBean.getObject();
        Assert.assertEquals("crap-http://bar", f.buildUri("http://bar"));
        Assert.assertEquals("crap-bar", f.buildUri("bar"));
    }
    
    
}

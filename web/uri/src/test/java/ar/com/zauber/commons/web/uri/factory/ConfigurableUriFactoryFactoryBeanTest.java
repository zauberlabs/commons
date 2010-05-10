/*
 * Copyright (c) 2010 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.uri.factory;


import static junit.framework.Assert.assertEquals;

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
        factoryBean = new ConfigurableUriFactoryFactoryBean(
                new IdentityUriFactory());
        factoryBean.setServletContext(servletContext);        
    }
    
    /** test method */
    @Test(expected = IllegalArgumentException.class)
    public final void testWithInvalidPrefix() throws Exception {
        factoryBean.setPrefixKey("basura");
        factoryBean.getObject();
    }
    
    /** test method */
    @Test
    public final void testWithPrefix() throws Exception {
        factoryBean.setPrefixKey("static:http://www.google.com");
        UriFactory uriFactory = factoryBean.getObject();
        assertEquals("http://www.google.com/hola", uriFactory.buildUri("/hola"));
    }

    /** test method */
    @Test
    public final void testWithServletPath() throws Exception {
        factoryBean.setPrefixKey("servlet-path");
        UriFactory uriFactory = factoryBean.getObject();
        assertEquals("/my-path/hola", uriFactory.buildUri("/hola"));
    }

    /** test method */
    @Test
    public final void testWithNoPrefix() throws Exception {
        UriFactory uriFactory = factoryBean.getObject();
        assertEquals("/hola", uriFactory.buildUri("/hola"));
    }
    
    /** test method */
    @Test
    public final void testWithVersion() throws Exception {
        factoryBean.setVersion("0.3");
        UriFactory uriFactory = factoryBean.getObject();
        assertEquals("/hola?v=0.3", uriFactory.buildUri("/hola"));
    }
    
}

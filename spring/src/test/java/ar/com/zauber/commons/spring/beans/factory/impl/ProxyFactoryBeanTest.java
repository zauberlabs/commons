/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.spring.beans.factory.impl;

import java.util.Arrays;
import java.util.Calendar;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import ar.com.zauber.commons.spring.beans.factory.impl.util.DateInvocationHandler;
import ar.com.zauber.commons.spring.beans.factory.impl.util.DateTestClass;
import ar.com.zauber.commons.spring.beans.factory.impl.util.DateTestInterface;

/**
 * Test de {@link ProxyFactoryBean}
 * 
 * @author Cecilia Hagge
 * @since Oct 23, 2009
 */
@ContextConfiguration(locations = {
        "/ar/com/zauber/commons/spring/spring-test-proxyfactorybean.xml"
})
public class ProxyFactoryBeanTest  extends  AbstractJUnit4SpringContextTests {

    @Resource private DateTestInterface mutableDateBean;
    
    /** Test que prueba que obtengo la instancia que le setee al invocacion 
     * handler a través del factory bean*/
    @Test
    public final void getObject() {
        final DateInvocationHandler ih = new DateInvocationHandler();
        Calendar c = Calendar.getInstance();
        c.set(2009, 8, 10);
        ih.setTarget(new DateTestClass(c.getTime()));
        final ProxyFactoryBean proxy = 
            new ProxyFactoryBean(Arrays.asList(new Class<?>[] {
                    DateTestInterface.class}), ih);
        try {
            final DateTestInterface mutableDate = (DateTestInterface) 
                proxy.getObject();
            Assert.assertEquals(0, c.getTime().compareTo(mutableDate.getDate()));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertFalse(true);
        }
    }

    /** Test que prueba que obtengo una fecha como la instanciada en 
     * Invocationhandler pero desde spring */
    @Test
    public final void testGetObjectByBean() {
        Assert.assertNotNull(mutableDateBean);
        Calendar c = Calendar.getInstance();
        c.set(2009, 7, 10, 0, 0, 0);
        Assert.assertEquals(c.getTime().getYear(), 
                mutableDateBean.getDate().getYear());
        Assert.assertEquals(c.getTime().getMonth(), 
                mutableDateBean.getDate().getMonth());
        Assert.assertEquals(c.getTime().getDay(), 
                mutableDateBean.getDate().getDay());
    }
    
}

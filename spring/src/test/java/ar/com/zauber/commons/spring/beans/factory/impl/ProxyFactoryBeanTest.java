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

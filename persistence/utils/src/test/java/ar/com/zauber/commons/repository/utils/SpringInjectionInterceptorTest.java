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
package ar.com.zauber.commons.repository.utils;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import ar.com.zauber.commons.repository.test.model.DomainEmbeededEntity;
import ar.com.zauber.commons.repository.test.model.DomainEntityExample;
import ar.com.zauber.commons.repository.test.model.SomeService;

/**
 * Tests {@link SpringInjectionInterceptor}.
 * 
 * @author Juan F. Codagnone
 * @since Mar 12, 2009
 */
@ContextConfiguration(locations = {
    "classpath:ar/com/zauber/commons/repository/utils/injection-hibernate-mapping-spring.xml",
    "classpath:ar/com/zauber/commons/repository/utils/injection-hibernate-spring.xml"
})
public class SpringInjectionInterceptorTest 
     extends AbstractTransactionalJUnit4SpringContextTests {
    @Resource private HibernateTemplate hibernateTemplate;
    @Resource private SomeService someService;

    
    /** @see AbstractTransactionalSpringContextTests#onSetUp() */
    @Before
    public final void onSetUp()  {
        final DomainEntityExample e1 = new DomainEntityExample();
        e1.setDomainEmbeededEntity(new DomainEmbeededEntity());
        hibernateTemplate.saveOrUpdate(e1);
        hibernateTemplate.evict(e1);
    }
    
    
    /** test */
    @Test
    public final void testFoo() {
        final DomainEntityExample e2 = hibernateTemplate.get( 
              DomainEntityExample.class, 1L);
        Assert.assertSame(someService, e2.getService());
        Assert.assertSame(someService, e2.getSomeService());
        Assert.assertSame(someService, e2.getFoo());
        Assert.assertTrue(e2.isInitialized());
        final SomeService embeed = e2.getDomainEmbeededEntity().getSomeService();
        Assert.assertNotNull(embeed);
        Assert.assertSame(someService, embeed);
    }
}

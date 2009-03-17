/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
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

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.AbstractSingleSpringContextTests;
import org.springframework.test.AbstractTransactionalSpringContextTests;

import ar.com.zauber.commons.repository.Reference;
import ar.com.zauber.commons.repository.Repository;
import ar.com.zauber.commons.repository.test.model.DomainEntityExample;
import ar.com.zauber.commons.repository.test.model.SomeService;

/**
 * Tests {@link SpringInjectionInterceptor}.
 * 
 * @author Juan F. Codagnone
 * @since Mar 12, 2009
 */
public class SpringInjectionInterceptorTest extends 
        AbstractTransactionalSpringContextTests  {
    // CHECKSTYLE:ALL:OFF
    protected Repository repository;
    protected HibernateTemplate hibernateTemplate;
    protected SomeService someService;
    // CHECKSTYLE:ALL::ON
    
    /** constructor */
    public SpringInjectionInterceptorTest() {
        setPopulateProtectedVariables(true);
    }

    /** @see AbstractSingleSpringContextTests#getConfigLocations() */
    protected final String[] getConfigLocations() {
        final String base = "classpath:ar/com/zauber/commons/repository";
        return new String[]  {
            base + "/ds-spring.xml",
            base + "/utils/injection-hibernate-mapping-spring.xml",
            base + "/utils/injection-hibernate-spring.xml",
            "classpath:ar/com/zauber/commons/repository/repository-spring.xml",
            "classpath:ar/com/zauber/commons/repository/postprocessing-spring.xml",
        };
    }
    
    /** @see AbstractTransactionalSpringContextTests#onSetUp() */
    @Override
    protected final void onSetUp()  {
        final DomainEntityExample e1 = new DomainEntityExample();
        repository.saveOrUpdate(e1);
        hibernateTemplate.evict(e1);
    }
    
    
    /** test */
    public final void testFoo() {
        final DomainEntityExample e2 = repository.retrieve(
                new Reference<DomainEntityExample>(
              DomainEntityExample.class, 1L));
        assertSame(someService, e2.getService());
        assertSame(someService, e2.getSomeService());
        assertTrue(e2.isInitialized());
    }
    
}

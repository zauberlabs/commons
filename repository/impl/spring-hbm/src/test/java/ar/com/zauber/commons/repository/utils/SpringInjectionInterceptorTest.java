/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
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
    }
    
}

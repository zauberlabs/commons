/*
 * Copyright (c) 2008 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.spring.configurers;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

/**
 * Test de unidad de {@link SystemPropertyPlaceholderConfigurer}.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Mar 23, 2008
 */
public class SystemPropertyPlaceholderConfigurerTest extends TestCase {

    /** test */
    public final void testFoo() {
        
        System.getProperties().put("spring-test-system", "4");
        final ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "classpath:spring-test-system.xml");
        final CheckBean i = (CheckBean) ctx.getBean("foo");
        assertEquals(i.getI(), 4);
    }
}

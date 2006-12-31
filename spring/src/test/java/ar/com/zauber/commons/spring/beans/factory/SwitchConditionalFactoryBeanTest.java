/*
 * Copyright (c) 2006 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.spring.beans.factory;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import junit.framework.TestCase;


/**
 * Testeos de unidad para {@link SwitchConditionalFactoryBean}.
 * 
 * @author Juan F. Codagnone
 * @since Aug 5, 2006
 */
public class SwitchConditionalFactoryBeanTest extends TestCase {

    /** testeo de unidad */
    public void testFoo() {
        final BeanFactory testFactory = new XmlBeanFactory(
                new ClassPathResource("spring-test-switch.xml"));
        assertEquals("es aretha", testFactory.getBean("test1"));
    }
}

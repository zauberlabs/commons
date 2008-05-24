/*
 * Copyright (c) 2005-2008 Zauber S.A. <http://www.zauber.com.ar/>
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

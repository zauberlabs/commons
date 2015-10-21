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
package ar.com.zauber.commons.spring.beans.factory.impl;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Prueba de {@link BooleanSystemPropertyCaseBlock}.
 * 
 * @author Juan F. Codagnone
 * @since Jun 10, 2008
 */
public class BooleanSystemPropertyCaseBlockTest extends TestCase {
    
    /** ehh     */
    public final void testUnset() {
        try {
            final ApplicationContext ctx = new ClassPathXmlApplicationContext(
                    "classpath:spring-test-switch-systemboolean.xml");
            final String s = (String) ctx.getBean("test1");
            assertEquals("mundo", s);
        } finally {
            System.getProperties().remove("babla.activar");
        }
    }
    
    /** ehh     */
    public final void testTrue() {
        System.getProperties().put("babla.activar", "true");
        try {
            final ApplicationContext ctx = new ClassPathXmlApplicationContext(
                    "classpath:spring-test-switch-systemboolean.xml");
            final String s = (String) ctx.getBean("test1");
            assertEquals("hola", s);
        } finally {
            System.getProperties().remove("babla.activar");
        }
    }
    
}

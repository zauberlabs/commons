/**
 * Copyright (c) 2005-2012 Zauber S.A. <http://www.zaubersoftware.com/>
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
    public final void testFoo() {
        final BeanFactory testFactory = new XmlBeanFactory(
                new ClassPathResource("spring-test-switch.xml"));
        assertEquals("es aretha", testFactory.getBean("test1"));
    }
}

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
package ar.com.zauber.commons.conversion.spring.schema;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import ar.com.zauber.commons.conversion.ConfigurableJavaBeanConverter;
import ar.com.zauber.commons.conversion.ConversionContext;
import ar.com.zauber.commons.conversion.spring.schema.testClasses.A;
import ar.com.zauber.commons.conversion.spring.schema.testClasses.B;


/**
 * Retrieves a ConfigurableJavaBeanConverter from an ApplicationContext.
 * 
 * 
 * @author Juan Edi
 * @since Nov 18, 2009
 */
@ContextConfiguration(locations = {
        "/ar/com/zauber/commons/converson/spring/schema/example.xml"
})
public class ConversionNamespaceHandlerTest 
     extends AbstractJUnit4SpringContextTests  {
    @Resource private ConfigurableJavaBeanConverter<A, B> configurableConverter;
    
    
    /** Tests if a ConfigurableJavaBeanConverter can be parsed from the XML. */
    @Test
    public final void beanTest() {
       Assert.assertNotNull(configurableConverter); 
       
       A sourceObject = new A(5);
       
       B targetObject = configurableConverter.convert(sourceObject,
                                                       new ConversionContext());

       Assert.assertEquals(sourceObject.getIntegerField(), 
               targetObject.getIntegerField());
       Assert.assertEquals(sourceObject.getIntegerField(),
               targetObject.getAnotherNameIntegerField());
       Assert.assertEquals(new Integer(sourceObject.getIntegerField() + 1), 
               targetObject.getPlusOneIntegerField());
       Assert.assertEquals(sourceObject.getClass().getName(),
               targetObject.getOtherClassName());
    
    }
    
}

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
package ar.com.zauber.commons.message.impl.message;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.junit.Before;
import org.junit.Test;

import ar.com.zauber.commons.dao.resources.StringResource;
import ar.com.zauber.commons.message.Message;
import ar.com.zauber.commons.message.NotificationAddress;
import ar.com.zauber.commons.message.impl.mail.JavaMailEmailAddress;
/**
 * Test de {@link VelocityEngineMessageTemplate}
 * 
 * 
 * @author Cecilia Hagge
 * @since 09/03/2011
 */
public class VelocityEngineMessageTemplateTest {

    private VelocityMessageTemplate velocityEngineTemplate;
    private final NotificationAddress address = new JavaMailEmailAddress("foo@bar");  
    private VelocityEngine velocityEngine; 
    
    /**
     * Before
     */
    @Before
    public final void setUp() throws Exception {
        velocityEngine = new VelocityEngine(getClass().getResource("velocity.properties").toExternalForm().substring(5));    
    }
    
    /**
     * Null Model
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testNullModel() {
        velocityEngineTemplate = new VelocityMessageTemplate(
                new StringResource("body"), "hola ${user}", address, "UTF-8", velocityEngine);
        velocityEngineTemplate.render(null);
    }
    
    /**
     * Empty Model
     */
    @Test
    public final void testEmptyModel() {
        velocityEngineTemplate = new VelocityMessageTemplate(
                new StringResource("body"), "hola ${user}", address, "UTF-8", velocityEngine);
        final Message message = velocityEngineTemplate.render(new HashMap<String, Object>());
        assertEquals("hola ${user}", message.getSubject());
        assertEquals("body", message.getContent());
    }
    
    /**
     * Null Velocity Engine
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testNullVelocityEngine() {
        velocityEngineTemplate = new VelocityMessageTemplate(
                new StringResource("body"), "hola ${user}", address, "UTF-8", null);
    }
    
    /**
     * Replace OK
     */
    @Test
    public final void testReplaceBody() {
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("user", "ceci");
        velocityEngineTemplate = new VelocityMessageTemplate(
                new StringResource("body"), "hola ${user}", address, "UTF-8", velocityEngine);
        final Message message = velocityEngineTemplate.render(map);
        assertEquals("hola ceci", message.getSubject());
        assertEquals("body", message.getContent());
        
        //TODO : Probar que genera otro log
        //assertNotNull(getClass().getResource("velocity.log"));
    }
    
    /**
     * OK
     */
    @Test
    public final void testReplaceSubject() {
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("user", "ceci");
        velocityEngineTemplate = new VelocityMessageTemplate(
                new StringResource("hola ${user}"), "subject", address, "UTF-8", velocityEngine);
        final Message message = velocityEngineTemplate.render(map);
        assertEquals("hola ceci", message.getContent());
        assertEquals("subject", message.getSubject());
        
        //TODO : Probar que genera otro log
        //assertNotNull(getClass().getResource("velocity.log"));
        
    }    
}

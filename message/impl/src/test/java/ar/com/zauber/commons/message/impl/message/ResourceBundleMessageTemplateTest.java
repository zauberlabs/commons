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
package ar.com.zauber.commons.message.impl.message;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.springframework.context.support.ResourceBundleMessageSource;

import ar.com.zauber.commons.message.Message;
import ar.com.zauber.commons.message.MessageTemplate;
import ar.com.zauber.commons.message.NotificationAddress;
import ar.com.zauber.commons.message.impl.mail.JavaMailEmailAddress;


/**
 * Tests {@link ResourceBundleMessageTemplate}
 */
public class ResourceBundleMessageTemplateTest extends TestCase {
    private final NotificationAddress address = new JavaMailEmailAddress("foo@bar");
    
    /** resolver to test */
    private final ResourceBundleMessageSource source 
    = new ResourceBundleMessageSource();
    {
        source.setBasename("messages");
    }
    
    /** unit test */
    public final void testRenderSubjectWithModel() {
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("user", "juan");
        final MessageTemplate template  = new ResourceBundleMessageTemplate(
                "body", "hola", address, source, "UTF-8");
        final Message msg = template.render(model); 
        assertEquals("body", msg.getContent());
        assertEquals("hola juan", msg.getSubject());
    }
    
    /** unit test */
    public final void testRenderSubjectWithOutModel() {
        final Map<String, Object> model = new HashMap<String, Object>();
        final MessageTemplate template  = new ResourceBundleMessageTemplate(
                "body", "hola", address, source, "UTF-8");
        final Message msg = template.render(model);
        
        assertEquals("body", msg.getContent());
        assertEquals("hola {0}", msg.getSubject());
    }
    
    /** unit test */
    public final void testRenderBodyWithOutModel() {
        final Map<String, Object> model = new HashMap<String, Object>();
        final MessageTemplate template  = new ResourceBundleMessageTemplate(
                "hola", "subject", address, source, "UTF-8");
        final Message msg = template.render(model);
        
        assertEquals("hola {0}", msg.getContent());
        assertEquals("subject", msg.getSubject());
    }
}

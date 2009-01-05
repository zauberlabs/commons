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
package ar.com.zauber.commons.message.impl.message;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import junit.framework.TestCase;
import ar.com.zauber.commons.message.Message;
import ar.com.zauber.commons.message.impl.mail.JavaMailEmailAddress;


/**
 * Unit test for {@link VelocityMessageResolver}.
 * 
 * @author Juan F. Codagnone
 * @since Apr 19, 2006
 */
public class ResourceBundleMessageResolverTest extends TestCase {
    /** resolver to test */
    private final ResourceBundleMessageFactory resolver;
    private final ResourceBundleMessageSource source;
    
    /** @throws on error */
    public ResourceBundleMessageResolverTest() throws Exception {
        source = new ResourceBundleMessageSource(); 
        source.setBasename("messages");
        resolver = new ResourceBundleMessageFactory(source);    
    }
    
    /** unit test */
    public final void testRenderSubjectWithModel() {
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("user", "juan");
        final Message msg = resolver.createMessage("body", "hola",
                model, new JavaMailEmailAddress("foo@bar"));
        
        assertEquals("body", msg.getContent());
        assertEquals("hola juan", msg.getSubject());
    }
    
    /** unit test */
    public final void testRenderSubjectWithOutModel() {
        final Object[] model = new Object[0];
        final Message msg = resolver.createMessage("body", "hola",
                model, new JavaMailEmailAddress("foo@bar"));
        
        assertEquals("body", msg.getContent());
        assertEquals("hola {0}", msg.getSubject());
    }
    
    /** unit test */
    public final void testRenderBodyWithOutModel() {
        final Object[] model = new Object[0];
        final Message msg = resolver.createMessage("hola", "subject",
                model, new JavaMailEmailAddress("foo@bar"));
        
        assertEquals("hola {0}", msg.getContent());
        assertEquals("subject", msg.getSubject());
    }
    
    /** unit test */
    public final void testRenderBodyWithModel() {
        final Object[] model = new Object[] {"juan"};
        final Message msg = resolver.createMessage("hola", "subject",
                model, new JavaMailEmailAddress("foo@bar"));
        
        assertEquals("hola juan", msg.getContent());
        assertEquals("subject", msg.getSubject());
    }


}

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
import java.util.Map;

import junit.framework.TestCase;
import ar.com.zauber.commons.message.Message;
import ar.com.zauber.commons.message.impl.mail.JavaMailEmailAddress;


/**
 * Unit test for {@link VelocityMessageResolver}.
 * 
 * @author Juan F. Codagnone
 * @since Apr 19, 2006
 */
public class VelocityMessageResolverTest extends TestCase {
    /** resolver to test */
    public final VelocityMessageResolver resolver;
    
    /** @throws on error */
    public VelocityMessageResolverTest() throws Exception {
        resolver = new VelocityMessageResolver();    
    }
    
    /** unit test */
    public final void testRenderSubjectWithModel() {
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("user", "juan");
        final Message msg = resolver.createMessage("body", "hola ${user}",
                model, new JavaMailEmailAddress("foo@bar"));
        
        assertEquals("body", msg.getContent());
        assertEquals("hola juan", msg.getSubject());
    }
    
    /** unit test */
    public final void testRenderSubjectWithOutModel() {
        final Map<String, Object> model = new HashMap<String, Object>();
        final Message msg = resolver.createMessage("body", "hola ${user}",
                model, new JavaMailEmailAddress("foo@bar"));
        
        assertEquals("body", msg.getContent());
        assertEquals("hola ${user}", msg.getSubject());
    }
    
    /** unit test */
    public final void testRenderBodyWithOutModel() {
        final Map<String, Object> model = new HashMap<String, Object>();
        final Message msg = resolver.createMessage("hola ${user}", "subject",
                model, new JavaMailEmailAddress("foo@bar"));
        
        assertEquals("hola ${user}", msg.getContent());
        assertEquals("subject", msg.getSubject());
    }
    
    /** unit test */
    public final void testRenderBodyWithModel() {
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("user", "juan");
        final Message msg = resolver.createMessage("hola ${user}", "subject",
                model, new JavaMailEmailAddress("foo@bar"));
        
        assertEquals("hola juan", msg.getContent());
        assertEquals("subject", msg.getSubject());
    }
}

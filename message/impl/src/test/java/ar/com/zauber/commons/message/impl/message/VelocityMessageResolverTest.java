/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
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

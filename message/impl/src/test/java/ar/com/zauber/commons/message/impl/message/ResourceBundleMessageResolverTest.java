/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
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

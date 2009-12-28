/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.message.impl.message;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import ar.com.zauber.commons.dao.resources.StringResource;
import ar.com.zauber.commons.message.MessagePart;
import ar.com.zauber.commons.message.MessageTemplate;
import ar.com.zauber.commons.message.MultipartMessage;
import ar.com.zauber.commons.message.NotificationAddress;
import ar.com.zauber.commons.message.impl.mail.JavaMailEmailAddress;
import ar.com.zauber.commons.message.message.templates.MultipartMessageTemplate;
import ar.com.zauber.commons.message.message.templates.PartTemplate;

/**
 * Tests multipart messages 
 * 
 * @author Christian Nardi
 * @since Dec 28, 2009
 */
public class MultipartMessageTest extends TestCase {
    private final NotificationAddress address = new JavaMailEmailAddress("foo@bar");
    /**
     * @throws Exception
     */
    public final void testMultipartMessage() throws Exception {
        final PartTemplate templatePlain = new VelocityMessagePartTemplate(
                "text/plain", new StringResource("hey! $you"));
        final PartTemplate templateHtml = new VelocityMessagePartTemplate(
                "text/html", new StringResource("<html><h1>hey!</h1> "
                        + "<h2>$you</h2></html>"));
        final MessageTemplate template = new MultipartMessageTemplate(
                Arrays.asList(new PartTemplate[]{templatePlain, templateHtml}), 
                "hi me", address);
        
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("you", "you");
        
        final MultipartMessage msg = (MultipartMessage) template.render(model);
        final MessagePart partPlain = msg.getPart("text/plain");
        final MessagePart partHtml = msg.getPart("text/html");
        final MessagePart partOther = msg.getPart("text/other");
        assertNotNull(partPlain);
        assertNotNull(partHtml);
        assertNull(partOther);
        assertEquals("hey! you", msg.getContent());
        assertEquals("hey! you", partPlain.getContent());
        assertEquals("<html><h1>hey!</h1> <h2>you</h2></html>", 
                partHtml.getContent());
        assertEquals("hi me", msg.getSubject());
    }
}

/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.message.impl.message;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import org.apache.commons.lang.UnhandledException;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import ar.com.zauber.commons.message.MessageFactory;
import ar.com.zauber.commons.message.MessageTemplate;
import ar.com.zauber.commons.message.NotificationAddress;
import ar.com.zauber.commons.message.message.templates.AbstractMessageTemplate;

/**
 * {@link MessageTemplate} that uses Velocity to render
 * 
 * @author Juan F. Codagnone
 * @since Jun 20, 2009
 */
public class VelocityMessageTemplate extends AbstractMessageTemplate {
    
    /** Creates the VelocityMessageTemplate. */
    public VelocityMessageTemplate(final String content, final String subject,
            final NotificationAddress address) {
        super(content, subject, address);
    }

    static {
        try {
            Velocity.init();
        } catch (final Exception e) {
            throw new UnhandledException(e);
        }
    }
    
    /** @see MessageFactory#renderString(String, Map) */
    public final String renderString(final String message,
            final Map<String, Object> model) {
        final StringWriter writer = new StringWriter();
        final VelocityContext context = new VelocityContext(model);
        try {
            Velocity.evaluate(context, writer, "message", new StringReader(message));
        } catch(final Exception e) {
            throw new RuntimeException(e);
        }
        
        return writer.toString();
    }
}

/*
 * Copyright (c) 2005 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.message.impl.message;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

import ar.com.zauber.commons.message.Message;
import ar.com.zauber.commons.message.MessageFactory;
import ar.com.zauber.commons.message.NotificationAddress;


/**
 * A Message factory that renders the messages using velocity
 * 
 * @author Juan F. Codagnone
 * @since Oct 6, 2005
 */
public class VelocityMessageResolver extends AbstractMessageFactory {
    /** velocity engine */
    private final VelocityEngine ve = new VelocityEngine();
    
    /** 
     * Creates the VelocityMessageResolver.
     *  
     * @throws Exception on error
     */
    public VelocityMessageResolver() throws Exception {
        ve.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM, this);
        ve.init();
    }
    
    /** @see MessageFactory#renderString(java.lang.String, java.util.Map) */
    public final String renderString(final String message,
            final Map<String, Object> model) {
        final StringWriter writer = new StringWriter();
        final VelocityContext context = new VelocityContext(model);
        try {
            ve.evaluate(context, writer, "message", new StringReader(message));
        } catch(final Exception e) {
            throw new RuntimeException(e);
        }
        
        return writer.toString();
    }
}

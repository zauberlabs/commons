/*
 * Copyright (c) 2011 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.message.impl.message;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import ar.com.zauber.commons.dao.Resource;
import ar.com.zauber.commons.message.MessageTemplate;
import ar.com.zauber.commons.message.NotificationAddress;
import ar.com.zauber.commons.message.message.templates.AbstractMessageTemplate;

/**
 * {@link MessageTemplate} that uses Velocity Engine to render. The difference
 * between Velocity Singleton usage and VelocityEngine is that with this 
 * implementation you can create an instance of VelocityEngine on your own and 
 * inject it as a configurable bean (this lets you configure the 
 * velocity.properties as you wish instead of using the default one). 
 * If you chose not to inject your own velocity engine, 
 * it uses a default one provided by Velocity framework.
 * 
 * @author Cecilia Hagge
 * @since 09/03/2011
 */
public class VelocityEngineMessageTemplate extends AbstractMessageTemplate {
    
    private final VelocityEngine velocityEngine;
    
    /**
     * Creates the VelocityEngineMessageTemplate.
     */
    public VelocityEngineMessageTemplate(final Resource content, 
            final String subject, final NotificationAddress address, 
            final String charset, final VelocityEngine velocityEngine) {
        super(content, subject, address, charset);
        Validate.notNull(velocityEngine);
        this.velocityEngine = velocityEngine;
    }
    
    /**
     * Creates the VelocityEngineMessageTemplate.
     */
    public VelocityEngineMessageTemplate(final Resource content, 
            final String subject, final NotificationAddress address,
            final String charset) {
        super(content, subject, address, charset);
        this.velocityEngine = new VelocityEngine();
    }

    /** @see AbstractTemplate#renderString(String, Map) */
    @Override
    protected final String renderString(final String message, 
            final Map<String, Object> model) {
        final StringWriter writer = new StringWriter();
        final VelocityContext context = new VelocityContext(model);
        try {
            this.velocityEngine.evaluate(context, writer, "message", new StringReader(message));
        } catch(final Exception e) {
            throw new RuntimeException(e);
        }
        
        return writer.toString();
    }

}

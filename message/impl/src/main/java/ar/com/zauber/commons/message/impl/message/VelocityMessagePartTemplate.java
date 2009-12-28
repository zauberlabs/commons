/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.message.impl.message;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import ar.com.zauber.commons.dao.Resource;
import ar.com.zauber.commons.message.message.templates.AbstractMessagePartTemplate;
import ar.com.zauber.commons.message.message.templates.AbstractTemplate;
import ar.com.zauber.commons.message.message.templates.PartTemplate;

/**
 * {@link PartTemplate} that uses Velocity
 * 
 * @author Christian Nardi
 * @since Dec 28, 2009
 */
public class VelocityMessagePartTemplate 
    extends AbstractMessagePartTemplate implements PartTemplate {
    /** Creates the VelocityMessageTemplate. */
    public VelocityMessagePartTemplate(final String contentType, 
            final Resource resource) {
        super(resource, contentType);
    }

    /** @see AbstractTemplate#renderString(java.lang.String, java.util.Map) */
    @Override
    protected final String renderString(final String message, 
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

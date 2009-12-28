/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.message.message.templates;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.message.Message;
import ar.com.zauber.commons.message.MessagePart;
import ar.com.zauber.commons.message.MessageTemplate;
import ar.com.zauber.commons.message.NotificationAddress;
import ar.com.zauber.commons.message.message.MultipartMessageImpl;

/**
 * Message template for multipart messages 
 * It delegates the actual render in other templates 
 * @author Christian Nardi
 * @since Dec 28, 2009
 */
public class MultipartMessageTemplate implements MessageTemplate {
    private final List<PartTemplate> templates;
    private final String subject;
    private final NotificationAddress address;
    
    
    /**
     * Creates the MultipartMessageTemplate.
     * 
     * @param templates relates contenttypes with templates
     */
    public MultipartMessageTemplate(final List<PartTemplate> templates,
            final String subject, final NotificationAddress address) {
        super();
        Validate.notEmpty(templates);
        Validate.notEmpty(subject);
        Validate.notNull(address);
        this.templates = templates;
        this.subject = subject;
        this.address = address;
    }


    /** @see ar.com.zauber.commons.message.MessageTemplate#render(java.util.Map) */
    public final Message render(final Map<String, Object> model) {
        final Collection<MessagePart> parts = new ArrayList<MessagePart>(
                templates.size());
        for (PartTemplate template : templates) {
            parts.add(template.createPart(model));
        }
        return new MultipartMessageImpl(parts, subject, address);
    }

}

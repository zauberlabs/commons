/*
 * Copyright (c) 2006 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.message.impl.message;

import java.util.Map;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.message.Message;
import ar.com.zauber.commons.message.MessageFactory;
import ar.com.zauber.commons.message.NotificationAddress;


/**
 * Implements only createMessagte
 * 
 * @author Juan F. Codagnone
 * @since Mar 15, 2006
 */
public abstract class AbstractMessageFactory implements MessageFactory {

    /** @see MessageFactory#createMessage(String, String, Map, 
     *                                                  NotificationAddress) */
    public final Message createMessage(final String stringMessage, 
            final String subject, final Map<String, Object> model,
            final NotificationAddress address) {
        
        Validate.notNull(stringMessage);
        Validate.notNull(model);
        
        return new StringMessage(renderString(stringMessage, model), 
                renderString(subject, model),
                address);
    }
}

/*
 * Copyright (c) 2005 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.message.impl.message;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.message.Message;
import ar.com.zauber.commons.message.NotificationAddress;


/**
 * Dummy implementation of Message
 *
 * @author Juan F. Codagnone
 * @since Oct 6, 2005
 */
public class StringMessage implements Message {
    /** @see #StringMessage(String, String) */
    private final String message;
    
    /** @see #StringMessage(String, String) */
    private final String subject;
    
    /** @see #StringMessage(String, String) */
    private final NotificationAddress fromAddress;
    
    /**
     * Creates the StringMessage.
     *
     * @param message a string message
     * @param subject subject of the message
     * @param fromAddress  who is sending the message?
     */
    public StringMessage(final String message, final String subject, 
            final NotificationAddress fromAddress) {
        Validate.notNull(message);
        Validate.notNull(subject);
        Validate.notNull(fromAddress);
        
        this.message = message;
        this.subject = subject;
        this.fromAddress = fromAddress;
    }
    
    /** @see ar.com.zauber.eventz.domain.notify.Message#getContent() */
    public final String getContent() {
        return message;
    }
    
    /** @see ar.com.zauber.eventz.domain.notify.Message#getReplyToAddress() */
    public final NotificationAddress getReplyToAddress() {
        return fromAddress;
    }
    
    /** @see ar.com.zauber.eventz.domain.notify.Message#getSubject() */
    public final String getSubject() {
        return subject;
    }
}

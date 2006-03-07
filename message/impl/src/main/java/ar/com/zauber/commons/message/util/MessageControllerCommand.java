/*
 * Copyright (c) 2005 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.message.util;

import java.util.Map;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.message.MessageFactory;
import ar.com.zauber.commons.message.NotificationAddress;
import ar.com.zauber.commons.message.NotificationStrategy;


/**
 * Contiene toda la información necesaria para que un controlador web envie un
 * mensaje. Ahorra el pasaje de multiples parametros al constructor del 
 * controlador.
 *
 * @author Juan F. Codagnone
 * @since Nov 30, 2005
 */
public class MessageControllerCommand  {
    /** notification strategy used to send the message */
    private NotificationStrategy notificationStrategy;
    /** message text */
    private String messageText;
    /** the subject of the message */
    private String messageSubject;
    /** from address for the message */
    private NotificationAddress messageFrom;
    /** factory used to create the message */
    private MessageFactory messageFactory;
    
    /**
     * 
     * Creates the MessageControllerCommand.
     *
     * @param notificationStrategy notification strategy used to send the 
     *                             confirmation of email change
     * @param messageText Text sent in the confirmation of an email
     *                             change
     * @param messageSubject the subject of the email change message
     * @param messageFrom the address used to set the email change...
     * @param messageFactory message factory used to create the message for 
     *                            email change
     */
    public MessageControllerCommand(
            final NotificationStrategy notificationStrategy, 
            final String messageText,
            final String messageSubject,
            final NotificationAddress messageFrom,
            final MessageFactory messageFactory) {        
        Validate.notNull(notificationStrategy, "notificationStrategy");
        Validate.notNull(messageText, "messageText");
        Validate.notNull(messageSubject,
                                                  "changeEmailMessageSubject");
        Validate.notNull(messageFrom, "changeEmailMessageFrom");
        Validate.notNull(messageFactory, "messageFactory");
        
        this.notificationStrategy = notificationStrategy;
        this.messageText = messageText;
        this.messageSubject = messageSubject;
        this.messageFrom = messageFrom;
        this.messageFactory = messageFactory;
        
    }
    
    /**
     * Sends a Message with the model to to[]
     * @param model model of the message
     * @param to reciver
     */
    public final void sendMessage(final Map<String, Object> model,
            final NotificationAddress [] to) {
        notificationStrategy.execute(to, messageFactory.createMessage(
                messageText, messageSubject, model, messageFrom));
    }
}

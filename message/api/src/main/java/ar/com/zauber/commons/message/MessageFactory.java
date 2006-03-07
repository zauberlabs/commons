/*
 * Copyright (c) 2005 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.message;

import java.util.Map;


/**
 * Creates Messages
 *
 * @author Juan F. Codagnone
 * @since Oct 6, 2005
 */
public interface MessageFactory {

    /**
     * @param stringMessage the message of the message
     * @param subject the subject of the message
     * @param model the data model
     * @param address the from address
     * 
     * @return a Message resolving stringMessage and the model
     */
    Message createMessage(String stringMessage, String subject,
            Map<String, Object> model, NotificationAddress address);
    
    /**
     * Utility
     * 
     * @param message the message of the message
     * @param model the data model
     * @return a renderer message using model
     */
    String renderString(String message, Map<String, Object> model);
}

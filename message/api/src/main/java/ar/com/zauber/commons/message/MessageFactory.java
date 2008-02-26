/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
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
     * @param stringMessage the message of the message
     * @param subject the subject of the message
     * @param model the data model
     * @param address the from address
     * 
     * @return a Message using params as positional arguments replacing the
     * apearence of ${0} with them.
     */
    Message createMessage(String stringMessage, String subject,
            Object[] params, NotificationAddress address);
    
    /**
     * Utility
     * 
     * @param message the message of the message
     * @param model the data model
     * @return a renderer message using model
     */
    String renderString(String message, Map<String, Object> model);
    
    /**
     * Utility, the message has to use ${0} and params are relative to the
     * order they appear.
     * 
     * @param message the message of the message
     * @param params the data model
     * @return a renderer message using params
     */
    String renderString(String message, Object[] params);
}

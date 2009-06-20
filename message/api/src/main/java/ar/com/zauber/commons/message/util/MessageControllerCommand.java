/**
 * Copyright (c) 2005-2009 Zauber S.A. <http://www.zauber.com.ar/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ar.com.zauber.commons.message.util;

import java.util.Map;

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
        validateNotNull(notificationStrategy, "notificationStrategy");
        validateNotNull(messageText, "messageText");
        validateNotNull(messageSubject,
                                                  "changeEmailMessageSubject");
        validateNotNull(messageFrom, "changeEmailMessageFrom");
        validateNotNull(messageFactory, "messageFactory");
        
        this.notificationStrategy = notificationStrategy;
        this.messageText = messageText;
        this.messageSubject = messageSubject;
        this.messageFrom = messageFrom;
        this.messageFactory = messageFactory;
        
    }
    
    /**
     * <p>Validate an argument, throwing <code>IllegalArgumentException</code>
     * if the argument is <code>null</code>.</p>
     */
    public static void validateNotNull(final Object object, final String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
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

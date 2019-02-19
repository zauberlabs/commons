/**
 * Copyright (c) 2005-2015 Zauber S.A. <http://flowics.com/>
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

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.message.Message;
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
    private final NotificationStrategy notificationStrategy;
    private final MessageFactory       messageFactory;
    private final String               viewName;
    
    /**
     * 
     * Creates the MessageControllerCommand.
     *
     * @param notificationStrategy notification strategy used to send the 
     *                             confirmation of email change
     * @param messageFactory message factory used to create the message for 
     *                            email change
     * @param viewName asociado                           
     */
    public MessageControllerCommand(
            final NotificationStrategy notificationStrategy,
            final MessageFactory       messageFactory,
            final String               viewName) {        
        Validate.notNull(notificationStrategy, "notificationStrategy");
        Validate.notNull(messageFactory,       "messageFactory");
        Validate.notNull(viewName,             viewName);
        
        this.notificationStrategy = notificationStrategy;
        this.messageFactory       = messageFactory;
        this.viewName             = viewName;
    }
    
    /**
     * Sends a Message with the model to to[]
     * @param model model of the message
     */
    public final void sendMessage(final Map<String, Object> model,
            final NotificationAddress [] to) {
        notificationStrategy.execute(to, render(model));
    }

    public Message render(final Map<String, Object> model) {
        return messageFactory.createMessage(viewName, model);
    }
}

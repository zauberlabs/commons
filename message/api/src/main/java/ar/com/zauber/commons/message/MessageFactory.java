/*
 * Copyright (c) 2005-2008 Zauber S.A. <http://www.zauber.com.ar/>
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

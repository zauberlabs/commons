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
    
    /** @see MessageFactory#createMessage(String, String, Map, 
     *                                                  NotificationAddress) */
    public final Message createMessage(final String stringMessage, 
            final String subject, final Object[] model,
            final NotificationAddress address) {
        
        Validate.notNull(stringMessage);
        Validate.notNull(model);
        
        return new StringMessage(renderString(stringMessage, model), 
                renderString(subject, model),
                address);
    }

    
}

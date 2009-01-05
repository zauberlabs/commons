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

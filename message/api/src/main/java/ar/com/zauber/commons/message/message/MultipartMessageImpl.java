/**
 * Copyright (c) 2005-2012 Zauber S.A. <http://www.zaubersoftware.com/>
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
package ar.com.zauber.commons.message.message;

import java.util.Collection;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.message.MessagePart;
import ar.com.zauber.commons.message.MultipartMessage;
import ar.com.zauber.commons.message.NotificationAddress;

/**
 * Simple impl for {@link MultipartMessage} 
 * 
 * @author Christian Nardi
 * @since Dec 28, 2009
 */
public class MultipartMessageImpl implements MultipartMessage {

    private static final String PLAINT_CONTENT_TYPE = "text/plain";
    private Collection<MessagePart> parts;
    private String subject;
    private NotificationAddress fromAddress;

    /**
     * Creates the MultipartMessageImpl.
     *
     * @param parts a list of parts for this message
     * @param subject subject of the message
     * @param fromAddress  who is sending the message?
     */
    public MultipartMessageImpl(final Collection<MessagePart> parts, 
            final String subject, 
            final NotificationAddress fromAddress) {
        Validate.notEmpty(parts);
        Validate.notNull(subject);
        Validate.notNull(fromAddress);
        this.parts = parts;
        this.subject = subject;
        this.fromAddress = fromAddress;
    }
    
    /** @see MultipartMessage#getPart(java.lang.String) */
    public final MessagePart getPart(final String contentType) {
        Validate.notEmpty(contentType);
        for (MessagePart part : parts) {
            if (part.isContentType(contentType)) {
                return part;
            }
        }
        return null;
    }

    /** @see ar.com.zauber.commons.message.MultipartMessage#getParts() */
    public final Collection<MessagePart> getParts() {
        return parts;
    }

    /** @see ar.com.zauber.commons.message.Message#getContent() */
    public final String getContent() {
        final MessagePart plainPart = getPart(PLAINT_CONTENT_TYPE);
        if (plainPart != null) {
            return (String) plainPart.getContent();
        } else {
            return "";
        }
        
    }

    /** @see ar.com.zauber.commons.message.Message#getReplyToAddress() */
    public final NotificationAddress getReplyToAddress() {
        return fromAddress;
    }

    /** @see ar.com.zauber.commons.message.Message#getSubject() */
    public final String getSubject() {
        return subject;
    }

    /** @see java.lang.Object#toString() */
    @Override
    public final String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("From:" + fromAddress);
        builder.append("\nTo:" + subject);
        for (MessagePart part : parts) {
            builder.append("ContentType:" + part.getContentType());
            builder.append("Part:" + part.getContent());
        }
        return builder.toString();
    }
}

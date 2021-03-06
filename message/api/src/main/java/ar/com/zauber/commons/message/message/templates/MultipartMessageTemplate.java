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
package ar.com.zauber.commons.message.message.templates;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.message.Message;
import ar.com.zauber.commons.message.MessagePart;
import ar.com.zauber.commons.message.MessageTemplate;
import ar.com.zauber.commons.message.NotificationAddress;
import ar.com.zauber.commons.message.message.ExtendedMultipartMessageImpl;
import ar.com.zauber.commons.message.message.MultipartMessageImpl;

/**
 * Message template for multipart messages 
 * It delegates the actual render in other templates 
 * @author Christian Nardi
 * @since Dec 28, 2009
 */
public class MultipartMessageTemplate implements MessageTemplate {
    private final List<PartTemplate> templates;
    private final String subject;
    private final NotificationAddress address;
    private final Map<String, String> headers;
    
    
    /**
     * Creates the MultipartMessageTemplate.
     * 
     * @param templates relates contenttypes with templates
     */
    public MultipartMessageTemplate(final List<PartTemplate> templates,
            final String subject, final NotificationAddress address) {
        this(templates, subject, address, new ConcurrentHashMap<String, String>());
    }
    
    /**
     * Creates the MultipartMessageTemplate.
     *
     * @param headers for the messages created
     */
    public MultipartMessageTemplate(
            final List<PartTemplate> templates,
            final String subject, final NotificationAddress address,
            final Map<String, String> headers) {
        super();
        Validate.notEmpty(templates);
        Validate.notEmpty(subject);
        Validate.notNull(address);
        Validate.notNull(headers);
        
        this.headers = headers;
        this.templates = templates;
        this.subject = subject;
        this.address = address;
    }


    /** @see ar.com.zauber.commons.message.MessageTemplate#render(java.util.Map) */
    public final Message render(final Map<String, Object> model) {
        final Collection<MessagePart> parts = new ArrayList<MessagePart>(
                templates.size());
        for (PartTemplate template : templates) {
            parts.add(template.createPart(model));
        }
        if(headers.size() > 0) {
            return new ExtendedMultipartMessageImpl(parts, subject, address, headers);
        } else {
            return new MultipartMessageImpl(parts, subject, address);
        }
    }

}

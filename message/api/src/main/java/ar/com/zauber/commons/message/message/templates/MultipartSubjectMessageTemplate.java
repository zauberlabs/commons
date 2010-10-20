/**
 * Copyright (c) 2005-2010 Zauber S.A. <http://www.zauber.com.ar/>
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

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.message.Message;
import ar.com.zauber.commons.message.MessagePart;
import ar.com.zauber.commons.message.MessageTemplate;
import ar.com.zauber.commons.message.NotificationAddress;
import ar.com.zauber.commons.message.message.MultipartMessageImpl;

/**
 * Como {@link MultipartMessageTemplate} pero renderea el subject con Velocity.
 * 
 * @author Andrés Moratti, Pablo Grigolatto
 * @since Sep 27, 2010
 */
public class MultipartSubjectMessageTemplate implements MessageTemplate {
    
    private final List<PartTemplate> templates;
    private final PartTemplate subjectTemplate;
    private final NotificationAddress address;
    
    /** Creates the MultipartVelocitySubjectMessageTemplate. */
    public MultipartSubjectMessageTemplate(
            final List<PartTemplate> templates,
            final PartTemplate subjectTemplate, 
            final NotificationAddress address) {
        
        Validate.notEmpty(templates);
        Validate.notNull(subjectTemplate);
        Validate.notNull(address);
        
        this.templates = templates;
        this.subjectTemplate = subjectTemplate;
        this.address = address;
    }

    /** @see MessageTemplate#render(Map) */
    public final Message render(final Map<String, Object> model) {
        final Collection<MessagePart> parts 
            = new ArrayList<MessagePart>(templates.size());
        
        for (PartTemplate template : templates) {
            parts.add(template.createPart(model));
        }
        
        final String subject 
            = subjectTemplate.createPart(model).getContent().toString();
        
        return new MultipartMessageImpl(parts, subject, address);
    }

}

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

import java.util.Map;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.exception.NoSuchEntityException;
import ar.com.zauber.commons.message.Message;
import ar.com.zauber.commons.message.MessageFactory;
import ar.com.zauber.commons.message.MessageTemplate;


/**
 * {@link MessageFactory} that resolves the messages using a map of 
 * {@link MessageTemplate}.
 * 
 * @author Juan F. Codagnone
 * @since Mar 15, 2006
 */
public class MessageTemplateMessageFactory implements MessageFactory {
    private final Map<String, MessageTemplate> templateMap;

    /*** 
     * @param templateMap map: the key is the template name, the value a 
     *                    MessageTemplate 
     */
    public MessageTemplateMessageFactory(
            final Map<String, MessageTemplate> templateMap) {
        Validate.notNull(templateMap);
        Validate.noNullElements(templateMap.keySet());
        Validate.noNullElements(templateMap.values());
        
        this.templateMap = templateMap;
    }
    
    /** @see MessageFactory#createMessage(String, Map) */
    public final Message createMessage(final String viewName, 
            final Map<String, Object> model) {
        final MessageTemplate template = templateMap.get(viewName);
        if(template == null) {
            throw new NoSuchEntityException(viewName);
        }
        
        return template.render(model);
    }
}

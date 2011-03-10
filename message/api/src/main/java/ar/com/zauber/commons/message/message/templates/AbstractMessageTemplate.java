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

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Resource;
import ar.com.zauber.commons.message.Message;
import ar.com.zauber.commons.message.MessageTemplate;
import ar.com.zauber.commons.message.NotificationAddress;
import ar.com.zauber.commons.message.message.StringMessage;

/**
 * Implements only createMessagte
 * 
 * @author Juan F. Codagnone
 * @since Mar 15, 2006
 */
public abstract class AbstractMessageTemplate 
    extends AbstractTemplate implements MessageTemplate {
    private final String content;
    private final String subject;
    private final NotificationAddress address;
    
    /** Creates the AbstractMessageTemplate. */
    public AbstractMessageTemplate(final Resource content, final String subject,
            final NotificationAddress address, final String charset) {
        Validate.notNull(content);
        Validate.notNull(subject);
        Validate.notNull(address);
        
        final ByteArrayOutputStream os = copyResource(content);
        try {
            this.content = os.toString(charset);
        } catch (UnsupportedEncodingException e) {
            throw new UnhandledException(e);
        }
        this.subject = subject;
        this.address = address;
    }

    
    /** @see MessageTemplate#render(Map) */
    public final Message render(final Map<String, Object> model) {
        Validate.notNull(model);
        for(final Entry<String, Object> entry : getExtraModel().entrySet()) {
            model.put(entry.getKey(), entry.getValue());
        }
        
        return new StringMessage(renderString(content, model), 
                renderString(subject, model),
                address);
    }
}

/**
 * Copyright (c) 2005-2011 Zauber S.A. <http://www.zaubersoftware.com/>
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
import ar.com.zauber.commons.message.MessagePart;
import ar.com.zauber.commons.message.MessageTemplate;
import ar.com.zauber.commons.message.message.StringMessagePart;

/**
 * Part template from a resource 
 * @author Christian Nardi
 * @since Dec 28, 2009
 */
public abstract class AbstractMessagePartTemplate 
    extends AbstractTemplate implements PartTemplate {
    private final String content;
    private final String contentType;

    /** Creates the AbstractMessagePartTemplate. */
    public AbstractMessagePartTemplate(final Resource content,
            final String contentType, final String charset) {
        Validate.notNull(content);
        Validate.notEmpty(contentType);

        final ByteArrayOutputStream os = copyResource(content);
        try {
            this.content = os.toString(charset);
        } catch (UnsupportedEncodingException e) {
            throw new UnhandledException(e);
        }
        this.contentType = contentType;
    }
    
    /** @see MessageTemplate#render(Map) */
    public final MessagePart createPart(final Map<String, Object> model) {
        for(final Entry<String, Object> entry : getExtraModel().entrySet()) {
            model.put(entry.getKey(), entry.getValue());
        }
        
        return new StringMessagePart(contentType, renderString(content, model));
    }
}

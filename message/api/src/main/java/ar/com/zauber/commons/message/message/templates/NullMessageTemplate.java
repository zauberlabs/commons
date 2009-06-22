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
package ar.com.zauber.commons.message.message.templates;

import java.io.InputStream;
import java.util.Map;

import ar.com.zauber.commons.dao.Resource;
import ar.com.zauber.commons.message.MessageFactory;
import ar.com.zauber.commons.message.NotificationAddress;


/**
 * Null implementation for {@link MessageFactory}
 * 
 * @author Juan F. Codagnone
 * @since Mar 15, 2006
 */
public class NullMessageTemplate extends AbstractMessageTemplate {

    /** @deprecated use the other constructor */
    public NullMessageTemplate(final String content, final String subject,
            final NotificationAddress address) {
        super(content, subject, address);
    }
    
    /** constructor */
    public NullMessageTemplate(final Resource content, final String subject,
            final NotificationAddress address) {
        super(content, subject, address);
    }

    /** @see MessageFactory#renderString(String, Map) */
    public final String renderString(final String message, 
            final Map<String, Object> model) {
        
        return message;
    }
}

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
    
    /** constructor */
    public NullMessageTemplate(final Resource content, final String subject,
            final NotificationAddress address, final String charset) {
        super(content, subject, address, charset);
    }

    /** @see MessageFactory#renderString(String, Map) */
    public final String renderString(final String message, 
            final Map<String, Object> model) {
        
        return message;
    }
}

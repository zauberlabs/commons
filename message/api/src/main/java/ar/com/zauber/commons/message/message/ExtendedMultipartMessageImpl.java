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
package ar.com.zauber.commons.message.message;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.message.HeaderMessage;
import ar.com.zauber.commons.message.MessagePart;
import ar.com.zauber.commons.message.MultipartMessage;
import ar.com.zauber.commons.message.NotificationAddress;
 
/**
 * {@link MultipartMessage} which includes headers  
 * 
 * 
 * @author Adolfo Joel Cueto
 * @since Sep 14, 2011
 */
public class ExtendedMultipartMessageImpl extends MultipartMessageImpl implements HeaderMessage {
    
    
    private final Map<String, String> headers;

    /**
     * Creates the ExtendedMultipartMessageImpl.
     *
     */
    public ExtendedMultipartMessageImpl(final Collection<MessagePart> parts, final String subject,
            final NotificationAddress fromAddress, final Map<String, String> headers) {
        super(parts, subject, fromAddress);
        Validate.notNull(headers);
        
        this.headers = headers;
    }
    
    public final Map<String, String> getHeaders() {
        return headers;
    }

}

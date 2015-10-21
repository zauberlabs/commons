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

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.message.MessagePart;

/**
 * String impl for {@link MessagePart} 
 * 
 * @author Christian Nardi
 * @since Dec 28, 2009
 */
public class StringMessagePart implements MessagePart {
    private final String contentType;
    private final String content;
    
    /**
     * Creates the StringMessagePart.
     *
     * @param contentType
     * @param content
     */
    public StringMessagePart(final String contentType, final String content) {
        super();
        Validate.notEmpty(contentType);
        Validate.notEmpty(content);
        this.contentType = contentType;
        this.content = content;
    }

    /** @see ar.com.zauber.commons.message.MessagePart#getContent() */
    public final Object getContent() {
        return content;
    }

    /** @see ar.com.zauber.commons.message.MessagePart#getContentType() */
    public final String getContentType() {
        return contentType;
    }

    /** @see MessagePart#isContentType(java.lang.String) */
    public final boolean isContentType(final String aContentType) {
        Validate.notEmpty(contentType);
        return contentType.equals(aContentType);
    }
}

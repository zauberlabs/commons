/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
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

package ar.com.zauber.commons.message;

import java.util.Collection;
/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */

/**
 * Multipart message 
 * 
 * @author Christian Nardi
 * @since Dec 28, 2009
 */
public interface MultipartMessage extends Message {
    /**
     * @return the parts for this message
     */
    Collection<MessagePart> getParts();
    
    /**
     * @param contentType the required content type
     * @return the messagePart for the given content type if
     * it exists, null otherwise
     */
    MessagePart getPart(String contentType);
    
}

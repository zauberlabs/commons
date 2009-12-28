/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.message;

/**
 * A part of a multipart message 
 * 
 * @author Christian Nardi
 * @since Dec 28, 2009
 */
public interface MessagePart {
    
    /**
     * @return the content type for this part
     */
    String getContentType();
    
    /**
     * @return true if getContentType.equals(contentType)
     */
    boolean isContentType(String contentType);
    
    /**
     * @return the content for this part
     */
    Object getContent();
}

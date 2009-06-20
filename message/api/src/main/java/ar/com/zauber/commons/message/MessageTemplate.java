/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.message;

import java.util.Map;

/**
 * Template for messages
 * 
 * @author Juan F. Codagnone
 * @since Jun 20, 2009
 */
public interface MessageTemplate {
    
    /** @return a message given a model */
    Message render(final Map<String, Object> model);
}

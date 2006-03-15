/*
 * Copyright (c) 2006 Zauber  -- All rights reserved
 */
package ar.com.zauber.commons.message.impl.message;

import java.util.Map;

import ar.com.zauber.commons.message.MessageFactory;


/**
 * Null implementation for {@link MessageFactory}
 * 
 * @author Juan F. Codagnone
 * @since Mar 15, 2006
 */
public class NullMessageFactory extends AbstractMessageFactory {

    /** @see MessageFactory#renderString(String, Map) */
    public final String renderString(final String message, 
            final Map<String, Object> model) {
        
        return message;
    }

}

/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.message.message.templates;

import java.util.Map;

import ar.com.zauber.commons.message.MessagePart;

/**
 * Factory for {@link MessagePart} 
 * 
 * @author Christian Nardi
 * @since Dec 28, 2009
 */
public interface PartTemplate {

    /**
     * @param model
     * @return
     */
    MessagePart createPart(Map<String, Object> model);

}

/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.transformation.sanitizing.api;

import org.w3c.dom.Node;

/**
 * Interface for a DOM tree sanitizer.
 * 
 * @author Fernando Resco
 * @since Mar 6, 2009
 */
public interface XmlSanitizer {
    
    /**
     * Sanitizes a DOM tree.
     * 
     * @param node
     */
    void sanitizeNodeTree(Node node);
    
}

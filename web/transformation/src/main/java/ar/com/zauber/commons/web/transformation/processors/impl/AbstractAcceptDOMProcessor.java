/**
 *  Copyright (c) 2008-2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.commons.web.transformation.processors.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Document;

import ar.com.zauber.commons.web.transformation.processors.DOMProcessor;


/**
 * Clase base para {@link DOMProcessor} que necesitan de una
 * expresion regular para saber si actual
 * 
 * 
 * @author Matías Tito
 * @since Nov 12, 2008
 */
public abstract class AbstractAcceptDOMProcessor implements DOMProcessor {

    /** @see DOMProcessor#accept(String) */
    public final boolean accept(final String uri) {
        final Matcher matcher = getPattern().matcher(uri);
        boolean ret;
        ret = false;
        if (matcher.lookingAt()) {
            ret = true;
        }
        return ret;
    }
    

    /** @see DOMProcessor#process(String, Document) */
    public final void process(final String uri, final Document document) {
        if(accept(uri)) {
            doProcess(document);
        }
    }
    
    /** @see DOMProcessor#process(String, Document) */ 
    public abstract void doProcess(Document document);
    
    /** @return la expresion regular del accept */
    public abstract Pattern getPattern();
}

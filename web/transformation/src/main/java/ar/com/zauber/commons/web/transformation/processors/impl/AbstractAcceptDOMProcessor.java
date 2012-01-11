/**
 * Copyright (c) 2005-2012 Zauber S.A. <http://www.zaubersoftware.com/>
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
